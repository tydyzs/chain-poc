package org.git.modules.clm.rcm.service;

import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.common.cache.DictCache;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;
import org.git.modules.clm.credit.service.ITbCrdStatisCsmService;
import org.git.modules.clm.credit.service.ITbCrdStatisCustypeService;
import org.git.modules.clm.credit.service.ITbCrdStatisOrgService;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.entity.*;
import org.git.modules.clm.rcm.mapper.RcmQuotaCheckMapper;
import org.git.modules.clm.rcm.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @version 1.0
 * @description: 限额检查服务类
 * @author: Haijie
 * @date: 2019/12/4
 **/
@AllArgsConstructor
@Service
@Slf4j
public class RcmQuotaCheckService {

	private IRcmIndexCreditService indexCreditService;
	private IRcmIndexBankService indexBankService;
	private RcmQuotaCheckMapper rcmQuotaCheckMapper;
	private IRcmNetCapitalService rcmNetCapitalService;
	private ITbCrdStatisCsmService crdStatisCsmService;
	private ITbCrdStatisOrgService crdStatisOrgService;
	private ITbCrdStatisCustypeService crdStatisCustypeService;
	private IRcmConfigService rcmConfigService;
	private IRcmConfigParaService rcmConfigParaService;
	private IRcmWarnInfoService rcmWarnInfoService;
	private ICsmPartyService csmPartyService;

	/**
	 * 校验业务限额指标
	 *
	 * @param bizInfo 业务信息
	 * @return
	 */
	public CheckResultVO check(CheckBizInfoVO bizInfo) {

		String orgNum = bizInfo.getOrgNum();
		Assert.notNull(orgNum, "机构号不能为空!");
		String customerNum = bizInfo.getCustomerNum();
		Assert.notNull(customerNum, "客户号不能为空!");
		List<RcmWarnInfo> warnInfos = new ArrayList<>();
		List<RcmConfigVO> list = rcmQuotaCheckMapper.listQuotaIndex(orgNum);

//		CsmParty party = csmPartyService.getById(customerNum);
//		Assert.notNull(party, customerNum + " 该客户不存在!");
//		String customerType = party.getCustomerType();//1 个人客户 2 公司客户 3 同业客户

		//循环该机构限额配置
		for (RcmConfigVO rcmConfig : list) {

			String quotaIndexNum = rcmConfig.getQuotaIndexNum();//限制指标编号
			String quotaNum = rcmConfig.getQuotaNum();//限额编号

			//指标维度，默认全维度，不校验业务指标要素
			String quotaIndexRange = StringUtil.isNotBlank(rcmConfig.getQuotaIndexRange()) ? rcmConfig.getQuotaIndexRange() : "0";//指标维度
			//如果不是全维度,则校验业务指标要素
			if (!"1".equals(quotaIndexRange)) {
				boolean result = checkIndexElement(rcmConfig.getQuotaIndexType(), quotaIndexNum, bizInfo);
				if (!result) {
					log.info("指标编号[" + quotaIndexNum + "]:指标名称[" + rcmConfig.getQuotaIndexName() + "],该指标要素检查不通过，已跳过此次限额指标校验！");
					continue;
				}
			}

			//查询三种统计口径
			String computingTarget = rcmConfig.getComputingTarget();//限额计算对象
			BigDecimal limitCredit = BigDecimal.ZERO;
			BigDecimal creditExpBalance = BigDecimal.ZERO;
			BigDecimal loanExpBalance = BigDecimal.ZERO;
			BigDecimal approveExpBalance = BigDecimal.ZERO;

			if ("1".equals(computingTarget)// 1、最大非同业单一客户
				|| "2".equals(computingTarget)//2、最大非同业单一集团
				|| "3".equals(computingTarget) //3、单一关联客户
				|| "7".equals(computingTarget)//7、最大单一同业客户
			) {
				TbCrdStatisCsm crdStatisCsm = new TbCrdStatisCsm();
				crdStatisCsm.setOrgNum(orgNum);//法人机构
				crdStatisCsm.setCustomerNum(customerNum);
				TbCrdStatisCsm data = crdStatisCsmService.getOne(Condition.getQueryWrapper(crdStatisCsm));
				if (data != null) {
					limitCredit = data.getLimitCredit();//授信总额 limit_credit
					creditExpBalance = data.getCreditExpBalance();//2-授信(敞口)余额 credit_exp_balance
					loanExpBalance = data.getLoanExpBalance();//1-贷款敞口余额 loan_exp_balance
					approveExpBalance = data.getApproveExpAmount();//3-批复敞口金额 approve_exp_amount
				}

			} else if ("9".equals(computingTarget)) {//8、全部客户
				TbCrdStatisOrg crdStatisOrg = new TbCrdStatisOrg();
				crdStatisOrg.setOrgNum(orgNum);//法人机构
				crdStatisOrg.setDataType("1");//实时数据
				TbCrdStatisOrg data = crdStatisOrgService.getOne(Condition.getQueryWrapper(crdStatisOrg));
				if (data != null) {
					limitCredit = data.getLimitCredit();//授信总额 limit_credit
					creditExpBalance = data.getCreditExpBalance();//2-授信(敞口)余额 credit_exp_balance
					loanExpBalance = data.getLoanExpBalance();//1-贷款敞口余额 loan_exp_balance
					approveExpBalance = data.getApproveExpAmount();//3-批复敞口金额 approve_exp_amount
				}
			} else if ("5".equals(computingTarget)//5、最大十家单一客户
				|| "6".equals(computingTarget)//6、最大十家集团客户
			) {
				List<TbCrdStatisCsm> topTenCustomers = rcmQuotaCheckMapper.listTopTenCustomer(orgNum);
				boolean isTop = false;//是否属于最大十家
				BigDecimal topTenlimitCredit = BigDecimal.ZERO;
				BigDecimal topTencreditExpBalance = BigDecimal.ZERO;
				BigDecimal topTenloanExpBalance = BigDecimal.ZERO;
				BigDecimal topTenapproveExpBalance = BigDecimal.ZERO;
				for (TbCrdStatisCsm item : topTenCustomers) {
					//计算最大十家之和
					topTenlimitCredit = topTenlimitCredit.add(item.getApproveExpAmount());
					topTencreditExpBalance = topTencreditExpBalance.add(item.getCreditExpBalance());
					topTenloanExpBalance = topTenloanExpBalance.add(item.getLoanExpBalance());
					topTenapproveExpBalance = topTenapproveExpBalance.add(item.getApproveExpAmount());
					if (customerNum.equals(item.getCustomerNum())) {
						isTop = true;
					}
				}
				if (isTop) {
					limitCredit = topTenlimitCredit;
					creditExpBalance = topTencreditExpBalance;
					loanExpBalance = topTenloanExpBalance;
					approveExpBalance = topTenapproveExpBalance;
				}
			} else if ("4".equals(computingTarget)) {//4、单一关联方所在集团客户
				// TODO
			} else if ("9".equals(computingTarget)) {//9、全部关联客户
				// TODO
			}


			String quotaIndexCaliber = rcmConfig.getQuotaIndexCaliber();//统计口径(1-贷款敞口余额；2-授信(敞口)余额；3-批复敞口金额；)

			//统计口径所使用的值
			BigDecimal quotaUsedAmt = BigDecimal.ZERO;//要加上当前申请的金额
			if ("1".equals(quotaIndexCaliber)) {
				quotaUsedAmt = quotaUsedAmt.add(loanExpBalance);//1-贷款敞口余额
			} else if ("2".equals(quotaIndexCaliber)) {
				quotaUsedAmt = quotaUsedAmt.add(creditExpBalance);//2-授信(敞口)余额
			} else if ("3".equals(quotaIndexCaliber)) {
				quotaUsedAmt = quotaUsedAmt.add(approveExpBalance);//3-批复敞口金额
			}


			//查询资本配置
			RcmNetCapitalVO params = new RcmNetCapitalVO();
			params.setOrgNum(orgNum);
			params.setNetState("1");//1生效；2失效
			RcmNetCapital rcmNetCapitalVO = rcmNetCapitalService.getOne(Condition.getQueryWrapper(params));
			Assert.notNull(rcmNetCapitalVO, orgNum + "该机构的[资本参数]没有配置，请联系管理员");
			BigDecimal netCapital = rcmNetCapitalVO.getNetCapital();//资本净额
			BigDecimal netPrimaryCapital = rcmNetCapitalVO.getNetPrimaryCapital();//一级资本净额
			BigDecimal netAssets = rcmNetCapitalVO.getNetAssets();//净资产
			Assert.notNull(netCapital, orgNum + "该机构[资本净额]配置值不能为空");
			Assert.notNull(netPrimaryCapital, orgNum + "该机构[一级资本净额]配置值不能为空");
			Assert.isTrue(netCapital.compareTo(BigDecimal.ZERO) == 1, orgNum + "该机构[资本净额]必须大于0");
			Assert.isTrue(netPrimaryCapital.compareTo(BigDecimal.ZERO) == 1, orgNum + "该机构[一级资本净额]必须大于0");

			//根据公式开始计算已用限额
			String computingMethod = rcmConfig.getComputingMethod();//限额计算方式： 1、统计口径/资本净额 2、统计口径/一级资本净额 3、统计口径/我行授信总额 4、金额上限
			String quotaControlType = "2";//阈值类型根据计算方法自动判断 1.金额 2.百分比
			String quotaTotalType = null;//限额总额类型
			BigDecimal quotaTotalSum = BigDecimal.ZERO;
			if ("1".equals(computingMethod)) {//1、统计口径/资本净额
				quotaTotalType = "1";
				quotaTotalSum = netCapital;
			} else if ("2".equals(computingMethod)) {// 2、统计口径/一级资本净额
				quotaTotalType = "2";
				quotaTotalSum = netPrimaryCapital;
			} else if ("3".equals(computingMethod)) {// 3、统计口径/我行授信总额
				quotaTotalType = "3";
				quotaTotalSum = limitCredit;
			} else if ("4".equals(computingMethod)) {
				// 4、金额上限，无需计算占比，= 统计口径
				quotaControlType = "1";
			}


			//判断三种限额阈值
			BigDecimal hisFrequencyA = rcmConfig.getHisFrequencyA() == null ? BigDecimal.ZERO : rcmConfig.getHisFrequencyA();//观察值历史触发次数
			BigDecimal hisFrequencyB = rcmConfig.getHisFrequencyB() == null ? BigDecimal.ZERO : rcmConfig.getHisFrequencyB();//预警值历史触发次数
			BigDecimal hisFrequencyC = rcmConfig.getHisFrequencyC() == null ? BigDecimal.ZERO : rcmConfig.getHisFrequencyC();//控制值历史触发次数
			//查询限额参数（多条），判断计算结果
			RcmConfigPara rcmConfigPara = new RcmConfigPara();
			rcmConfigPara.setQuotaIndexNum(quotaIndexNum);
			rcmConfigPara.setQuotaNum(quotaNum);
			rcmConfigPara.setUseOrgNum(orgNum);//生效机构
			rcmConfigPara.setControlNode(bizInfo.getControlNode());//控制节点 CD000259
			BigDecimal controlAmtValue = null;//金额控制值
			BigDecimal controlRatioValue = null;//比例控制值
			BigDecimal quotaUsedAmt_ = quotaUsedAmt.add(bizInfo.getAmt());//判断时加上当前发生额
			BigDecimal quotaUsedRatio_ = quotaUsedAmt_.divide(quotaTotalSum).multiply(new BigDecimal(100));//限额已用比率
			List<RcmConfigPara> paraList = rcmConfigParaService.list(Condition.getQueryWrapper(rcmConfigPara));
			for (RcmConfigPara para : paraList) {

				BigDecimal quotaControlAmt = para.getQuotaControlAmt();//阈值（余额）
				BigDecimal quotaControlRatio = para.getQuotaControlRatio();//阈值（占比）
//				String quotaControlType = para.getQuotaControlType();//阈值类型 1.金额 2.百分比
				String quotaLevel = para.getQuotaLevel();//阈值层级(1、控制值；2、预警值；3、观察值)CD000223
				String quotaLevelName = DictCache.getValue("CD000223", quotaLevel);
				boolean isPass = true;
				if ("1".equals(quotaControlType) && quotaControlAmt != null) {//1.金额
					Assert.notNull(quotaControlAmt, "机构限额设置的【" + orgNum + "】阈值类型【" + quotaLevelName + "】控制金额不能为空！");
					if ("1".equals(quotaLevel)) {//如果是控制值
						controlAmtValue = quotaControlAmt;
					}
					if (quotaUsedAmt_.compareTo(quotaControlAmt) == 1) {
						log.warn("限额余额阈值[" + quotaUsedAmt_ + "元]已经超过设置的阈值[" + quotaControlAmt + "元]");
						isPass = false;
					}
				} else if ("2".equals(quotaControlType) && quotaControlRatio != null) {//2.百分比
					Assert.notNull(quotaControlRatio, "机构限额设置的【" + orgNum + "】阈值类型【" + quotaLevelName + "】控制比例不能为空！");
					if ("1".equals(quotaLevel)) {//如果是控制值
						controlRatioValue = quotaControlRatio;
					}
					if (quotaUsedRatio_.compareTo(quotaControlRatio) == 1) {
						log.warn("限额占比阈值[" + quotaUsedRatio_ + "%]已经超过设置的阈值[" + quotaControlRatio + "%]");
						isPass = false;
					}
				}

				//如果触发了控制，根据控制的阈值层级，增加相应的触发次数
				if (!isPass) {
					if ("1".equals(quotaLevel)) {//1、控制值
						hisFrequencyA = hisFrequencyA.add(new BigDecimal(1));
					} else if ("2".equals(quotaLevel)) {//2、预警值
						hisFrequencyB = hisFrequencyB.add(new BigDecimal(1));
					} else if ("3".equals(quotaLevel)) {//3、观察值
						hisFrequencyC = hisFrequencyC.add(new BigDecimal(1));
					}

					RcmWarnInfo warnInfo = addWarnInfo(rcmConfig, para, bizInfo, quotaIndexCaliber);//增加预警信息
					warnInfos.add(warnInfo);//添加到预警列表里
				}


				//限额管控节点: 0、所有阶段；1、授信申请提交；2、授信申请流程中；3、授信审批；4、合同申请提交；5、合同申请审批6、放款申请提交
				String controlNode = para.getControlNode();//CD000259 限额管控节点 TODO
				String nodeMeasure = para.getNodeMeasure();//CD000260 管控节点的应对措施(1提示；2触发报备；3禁止操作)
				String measureLevel = para.getMeasureLevel();//CD000265 应对措施等级(1-一级；2-二级；3-三级)

			}

			//计算控制金额 = 总额 * 控制比例
			if (controlRatioValue != null && controlAmtValue == null) {
				controlAmtValue = quotaTotalSum.multiply(controlRatioValue).divide(new BigDecimal(100));
			}
			//计算控制比例 = 控制金额 / 总额
			if (controlRatioValue == null && controlAmtValue != null && controlAmtValue.compareTo(BigDecimal.ZERO) == 1) {
				controlRatioValue = controlAmtValue.divide(quotaTotalSum).subtract(new BigDecimal(100));
			}

			//将判读和计算结果存储到限额表里
			//将要更新的限额信息 限额可用金额 = 控制值 - 业务已用金额（统计口径）
			BigDecimal quotaUsedRatio = quotaUsedAmt.divide(quotaTotalSum).multiply(new BigDecimal(100));//限额已用比率
			RcmConfig rcmConfigRet = new RcmConfig();
			rcmConfigRet.setQuotaIndexNum(quotaIndexNum);//限额指标编号
			rcmConfigRet.setQuotaNum(quotaNum);//限额编号
			rcmConfigRet.setQuotaUsedAmt(quotaUsedAmt);//限额已用金额
			rcmConfigRet.setQuotaUsedRatio(quotaUsedRatio);//限额已用比率
			if (controlAmtValue != null && quotaUsedAmt != null) {
				rcmConfigRet.setQuotaFreeAmt(controlAmtValue.subtract(quotaUsedAmt));//限额可用金额
			}
			if (controlRatioValue != null && quotaUsedRatio != null) {
				rcmConfigRet.setQuotaFreeRatio(controlRatioValue.subtract(quotaUsedRatio));//限额可用比率
			}
			rcmConfigRet.setQuotaTotalSum(quotaTotalSum);//限额总额
			rcmConfigRet.setQuotaTotalType(quotaTotalType);//限额总额类型(1-资本净额；2-一级资本净额；3-我行授信总额；)
			rcmConfigRet.setHisFrequencyA(hisFrequencyA);//观察值历史触发次数
			rcmConfigRet.setHisFrequencyB(hisFrequencyB);//预警值历史触发次数
			rcmConfigRet.setHisFrequencyC(hisFrequencyC);//控制值历史触发次数
			rcmConfigRet.setHisFrequency(hisFrequencyA.add(hisFrequencyB).add(hisFrequencyC));//历史触发次数
			rcmConfigService.updateById(rcmConfigRet);

		}

		//返回检查结果：有预警信息说明检查没有通过
		CheckResultVO checkResult = new CheckResultVO();
		//循环预警信息的应对措施，并取出最大值（最高等级）
		List<Integer> controlTypes = new ArrayList<Integer>();//（0-通过 1-提示 2-触发报备 3-禁止操作）
		for (RcmWarnInfo warnInfo : warnInfos) {
			controlTypes.add(Integer.valueOf(warnInfo.getNodeMeasure()));//管控类型添加到controlType中
		}
		if (controlTypes != null && controlTypes.size() > 0) {
			checkResult.setControlType(String.valueOf(Collections.max(controlTypes)));//管控类型取数组中级别最大的
		} else {
			checkResult.setControlType(RcmConstant.QUOTA_NODE_MEASURE_PASS);//管控类型为通过
		}
		checkResult.setWarnInfos(warnInfos);
		log.info("机构[" + orgNum + "]限额检查结果：" + checkResult.getWarnMessage());
		return checkResult;
	}

	/**
	 * 增加限额预警信息
	 *
	 * @param rcmConfig
	 * @param para
	 * @param bizInfo
	 */
	public RcmWarnInfo addWarnInfo(RcmConfig rcmConfig, RcmConfigPara para, CheckBizInfoVO bizInfo, String quotaIndexCaliber) {
		RcmWarnInfo warnInfo = new RcmWarnInfo();
		warnInfo.setWarnNum(BizNumUtil.getBizNum("WN"));//预警信息编号
		//限额信息
		warnInfo.setQuotaNum(rcmConfig.getQuotaNum());//集中度限额编号
		warnInfo.setQuotaName(rcmConfig.getQuotaName());//限额名称
		warnInfo.setQuotaIndexNum(rcmConfig.getQuotaIndexNum());//限额指标编号
		warnInfo.setQuotaUsedAmt(rcmConfig.getQuotaUsedAmt());//限额已用金额-预警时点数
		warnInfo.setQuotaFreeAmt(rcmConfig.getQuotaFreeAmt());//限额可用金额-预警时点数
		warnInfo.setQuotaUsedRatio(rcmConfig.getQuotaUsedRatio());//限额已用比率-预警时点数
		warnInfo.setQuotaFreeRatio(rcmConfig.getQuotaFreeRatio());//限额可用比率-预警时点数
		warnInfo.setQuotaTotalSum(rcmConfig.getQuotaTotalSum());//限额总额
		warnInfo.setQuotaTotalType(rcmConfig.getQuotaTotalType());//限额总额类型(1-资本净额；2-一级资本净额；3-我行授信总额；)
		warnInfo.setUseOrgNum(rcmConfig.getUseOrgNum());//限额生效机构
		warnInfo.setTriggerTime(CommonUtil.getWorkDateTime());//触发时间
		warnInfo.setQuotaIndexCaliber(quotaIndexCaliber);
		//参数信息
		warnInfo.setTriggerLevel(para.getQuotaLevel());//阈值层级(1、观察；2、预警；3、控制)
		warnInfo.setQuotaControlAmt(para.getQuotaControlAmt());//预警阀值(余额)
//		warnInfo.setQuotaControlRatio(para.getQuotaControlRatio());//预警阀值(比例)
		warnInfo.setTriggerAmt(rcmConfig.getQuotaUsedAmt());//触发当前值(余额)
		warnInfo.setTriggerRatio(rcmConfig.getQuotaUsedRatio());//触发当前值(占比)
		warnInfo.setTriggerControlNode(para.getControlNode());//触发管控节点
		warnInfo.setNodeMeasure(para.getNodeMeasure());//应对措施(1-提示；2-触发报备；3-禁止操作)
		warnInfo.setMeasureLevel(para.getMeasureLevel());//对应措施等级(1-一级；2-二级；3。三级)
		//业务指标要素
		warnInfo.setTranSeqSn(bizInfo.getTranSeqSn());//关联业务流水号
		warnInfo.setBizNum(bizInfo.getBizNum());//关联业务编号
		warnInfo.setBizType(bizInfo.getBizType());//业务类型(1、额度申请  2、合同申请 3、放款申请4、同业业务申请)
		warnInfo.setUserNum(bizInfo.getUserNum());//业务客户经理
		warnInfo.setOrgNum(bizInfo.getOrgNum());//业务网点机构
		warnInfo.setCustomerNum(bizInfo.getCustomerNum());//客户编号
		warnInfo.setPartyName(bizInfo.getCustomerName());//客户名称
		warnInfo.setProductNum(bizInfo.getProductNum());//业务品种
		warnInfo.setAmt(bizInfo.getAmt());//发生金额
		warnInfo.setCurrencyCd(bizInfo.getCurrencyCd());//币种
		//其他信息
		warnInfo.setRecoYear(CommonUtil.getWorkDate().substring(0, 4));//发生年份
		warnInfo.setRecoMonth(CommonUtil.getWorkDate().substring(5, 7));//发生月份
		warnInfo.setCreateTime(CommonUtil.getWorkDateTime());//备用字段1
		warnInfo.setUpdateTime(CommonUtil.getWorkDateTime());//维护时间
		warnInfo.setRemark("");//备注
		rcmWarnInfoService.save(warnInfo);
		return warnInfo;

	}


	/**
	 * 校验业务指标要素
	 *
	 * @param quotaIndexType
	 * @param quotaIndexNum
	 * @param bizInfo
	 * @return
	 */
	public boolean checkIndexElement(String quotaIndexType, String quotaIndexNum, CheckBizInfoVO bizInfo) {
		//限额指标类型:1-同业限额指标
		if ("1".equals(quotaIndexType)) {
			RcmIndexBank indexBank = indexBankService.getById(quotaIndexNum);
			BeanUtils.copyProperties(indexBank, bizInfo);//复制属性
			if (indexBank.getRangeCustomer().contains(bizInfo.getCustomerType())//客户类型range_customer
				&& indexBank.getCurrency().contains(bizInfo.getCurrencyCd())//币种currency
				&& indexBank.getRangeCountry().contains(bizInfo.getRangeCountry())//国别range_country
				&& indexBank.getRangeProduct().contains(bizInfo.getProductNum())//产品编号range_product
				&& indexBank.getBusinessType().contains(bizInfo.getBusinessType())//业务类型business_type
				&& indexBank.getBussScene().contains(bizInfo.getBussScene())//业务场景buss_scene
			) {
				return true;

			}

		}
		//限额指标类型:2-非同业限额指标
		else if ("2".equals(quotaIndexType)) {

			RcmIndexCredit indexCredit = indexCreditService.getById(quotaIndexNum);
			BeanUtils.copyProperties(indexCredit, bizInfo);//复制属性
			if (indexCredit.getRangeCustomer().contains(bizInfo.getCustomerType())//客户类型range_customer
				&& indexCredit.getCurrency().contains(bizInfo.getCurrencyCd())//币种currency
				&& indexCredit.getRangeCountry().contains(bizInfo.getRangeCountry())//国别range_country
				&& indexCredit.getRangeProduct().contains(bizInfo.getProductNum())//产品编号range_product
				&& indexCredit.getRangeRegion().contains(bizInfo.getRangeRegion())//区域range_region
				&& indexCredit.getRangeIndustry().contains(bizInfo.getRangeIndustry())//行业range_industry
				&& indexCredit.getRangeTerm().contains(bizInfo.getRangeTerm())//期限范围range_term
				&& indexCredit.getRangerRiskMitigation().contains(bizInfo.getRangerRiskMitigation())//风险缓释ranger_risk_mitigation
			) {
				return true;

			}

		} else {
			log.warn("限额指标类型不正确: quotaIndexType = " + quotaIndexType + ", quotaIndexNum = " + quotaIndexNum);
		}
		return false;

	}


}
