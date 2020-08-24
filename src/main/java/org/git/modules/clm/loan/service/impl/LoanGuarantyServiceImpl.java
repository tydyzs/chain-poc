package org.git.modules.clm.loan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.modules.clm.common.constant.DictMappingConstant;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.credit.entity.TbCrdSurety;
import org.git.modules.clm.credit.service.ITbCrdSuretyService;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanGuarantyInfoRequestDTO;
import org.git.modules.clm.loan.service.ILoanGuarantyService;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.service.ICrdProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 押品信息同步 实现类
 *
 * @author zhouweijei
 */
@Slf4j
@Service
@Transactional
public class LoanGuarantyServiceImpl implements ILoanGuarantyService {
	@Autowired
	ITbCrdSuretyService crdSuretyService;
	@Autowired
	ICsmPartyService csmPartyService;
	@Autowired
	ICrdProductService crdProductService;
	@Autowired
	ICommonService commonService;

	private String customerNum = "";//客户编号
	private String customerType = "";//客户类型
	private Crd crd = new Crd();//额度表实体类

	/**
	 * 检查客户信息
	 *
	 * @param requestDTO 请求体
	 * @return 错误信息
	 */
	@Override
	public String checkCustomerInfo(LoanGuarantyInfoRequestDTO requestDTO) {
		customerNum = requestDTO.getCustomerNum();//客户编号
		try {
			CsmParty csmParty = csmPartyService.getById(customerNum);//查询结果
			/*检查客户是否存在*/
			if (null == csmParty) {
				//TODO 调用ECIF客户信息同步
				return "不存在客户编号为[" + customerNum + "]的客户。";
			}
			customerType = DictMappingConstant.customerTypeMap.get(requestDTO.getCustomerType());//客户类型
		} catch (Exception e) {
			e.printStackTrace();
			return "查询客户信息异常！";
		}
		return null;
	}

	/**
	 * 检查押品信息
	 *
	 * @param requestDTO 请求体
	 * @return 错误信息
	 */
	@Override
	public String checkPledgeInfo(LoanGuarantyInfoRequestDTO requestDTO) {
		try {
			/*	if (JxrcbBizConstant.OP_TYPE_ADD.equals(opType)) {
				*//*新增，判断押品是否存在*//*
				TbCrdSurety crdSurety = crdSuretyService.getById(requestDTO.getSuretyNum());//查询结果
				if (null != crdSurety) {
					return "已存在押品编号为[" + requestDTO.getSuretyNum() + "]的押品。";
				}
			} else if (JxrcbBizConstant.OP_TYPE_UPDATE.equals(opType) ||
				JxrcbBizConstant.OP_TYPE_DELETE.equals(opType)) {
				*//*修改或删除，判断押品是否存在*//*
				TbCrdSurety crdSurety = crdSuretyService.getById(requestDTO.getSuretyNum());//查询结果
				if (null == crdSurety) {
					return "不存在押品编号为[" + requestDTO.getSuretyNum() + "]的押品。";
				}

			} else {
				*//*未知操作类型*//*
				return "操作类型码值[" + opType + "]不存在。";
			}*/

			/* 传入客户类型和担保方式 额度类型得到额度产品业务品种关联关系表数据*/
			CrdProduct crdProduct = new CrdProduct();//查询条件对象
			crdProduct.setCustType(customerType);//客户类型
			crdProduct.setMainGuaranteeType(requestDTO.getGuaranteeType());//担保方式
			crdProduct.setCrdProductType(JxrcbBizConstant.CRD_TYPE_DB);//额度类型
			crd = crdProductService.selectCrd(crdProduct);
			if (crd == null || crd.getCrdProductNum() == null || crd.getSuperCrdNum() == null) {
				return "查询额度产品业务品种关联关系表，无法获得对应额度品种。";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "查询押品信息异常！";
		}
		return null;
	}

	/**
	 * 同步押品信息
	 *
	 * @param requestDTO 请求实体
	 * @return 错误信息
	 */
	@Override
	public String mergeGuarantyInfo(LoanGuarantyInfoRequestDTO requestDTO) {
		TbCrdSurety crdSurety = new TbCrdSurety();
		/*实体类赋值*/
		try {
			setCrdSurety(crdSurety, requestDTO);
			if (JxrcbBizConstant.OP_TYPE_ADD.equals(requestDTO.getOpType())) {
				/*新增*/
				crdSurety.setCreateTime(CommonUtil.getWorkDateTime());
				crdSuretyService.saveOrUpdate(crdSurety);
			} else if (JxrcbBizConstant.OP_TYPE_UPDATE.equals(requestDTO.getOpType())) {
				/*修改*/
				crdSurety.setUpdateTime(CommonUtil.getWorkDateTime());
				crdSuretyService.saveOrUpdate(crdSurety);
			} else if (JxrcbBizConstant.OP_TYPE_DELETE.equals(requestDTO.getOpType())) {
				/*删除*/
				crdSuretyService.removeById(requestDTO.getSuretyNum());
			}
			/*额度重算和统计*/
			String msg = commonService.guaranteeRecount(customerNum);
			if (null != msg) {
				return msg;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "同步押品信息异常！";
		}
		return null;
	}


	/**
	 * 抵质押物/保证人信息表实体类赋值
	 *
	 * @param crdSurety  抵质押物/保证人信息表实体类
	 * @param requestDTO 请求实体类
	 */
	private void setCrdSurety(TbCrdSurety crdSurety, LoanGuarantyInfoRequestDTO requestDTO) {
		/*通过公共方法获取二级、三级额度编号*/
//		String crdDetailNum = commonService.getCrdNum(customerNum, requestDTO.getOrgNum(), crd.getCrdProductNum());//三级额度编号
//		String crdMainNum = commonService.getCrdNum(customerNum, requestDTO.getOrgNum(), crd.getCrdProductNum());//二级额度编号

		/*根据请求信息 给需要存入数据库的实体类字段赋值*/
		crdSurety.setSuretyNum(requestDTO.getSuretyNum());//担保物编号
		crdSurety.setCrdMainPrd(crd.getCrdProductNum());//二级额度品种
//		crdSurety.setCrdMainNum(crdMainNum);//二级额度编号
		crdSurety.setCrdDetailPrd(crd.getSuperCrdNum());//三级额度品种
//		crdSurety.setCrdDetailNum(crdDetailNum);//三级额度编号
		crdSurety.setCustomerNum(requestDTO.getCustomerNum());//客户号
		crdSurety.setPledgeType(requestDTO.getPledgeType());//担保物类型
		crdSurety.setGuaranteeType(requestDTO.getGuaranteeType());//担保方式
		crdSurety.setPledgeType(requestDTO.getPledgeType());//担保物类型
		crdSurety.setPledgeName(requestDTO.getPledgeName());//担保物名称
		crdSurety.setCurrencyCd(JxrcbBizConstant.CURRENCY_CNY);//币种
		crdSurety.setExchangeRate(BigDecimal.ONE);//汇率
		crdSurety.setAmtAsses(requestDTO.getAmtAsses());//押品评估价值
		crdSurety.setAmtActual(requestDTO.getAmtActual());//押品权利价值
		crdSurety.setPledgeRate(requestDTO.getPledgeRate());//抵质押率
		crdSurety.setPledgeStatus(requestDTO.getPledgeStatus());//押品状态
		crdSurety.setTranDate(CommonUtil.getWorkDate());//交易日期
		crdSurety.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//经办系统
		crdSurety.setUserNum(requestDTO.getUserNum());//经办人
		crdSurety.setOrgNum(requestDTO.getOrgNum());//经办机构
	}

}
