package org.git.modules.clm.front.api.jxrcb;

import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.service.impl.TbCrdApproveServiceImpl;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.customer.service.impl.CsmPartyServiceImpl;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanProjectRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.loan.LoanProjectResponseDTO;
import org.git.modules.clm.loan.entity.CrdProject;
import org.git.modules.clm.loan.entity.CrdProjectEvent;
import org.git.modules.clm.loan.service.ICrdProjectEventService;
import org.git.modules.clm.loan.service.ICrdProjectService;
import org.git.modules.clm.loan.service.impl.CrdProjectEventServiceImpl;
import org.git.modules.clm.loan.service.impl.CrdProjectServiceImpl;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.clm.param.service.impl.CrdProductServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * 江西农信信贷系统-项目协议处理接口 CLM104
 *
 * @author caohaijie
 */
@Slf4j
@Transactional
public class LoanProjectAPI extends JxrcbAPI {

	private ICsmPartyService csmPartyService = SpringUtil.getBean(CsmPartyServiceImpl.class);
	private ICrdProjectEventService crdProjectEventService = SpringUtil.getBean(CrdProjectEventServiceImpl.class);
	private ICrdProjectService crdProjectService = SpringUtil.getBean(CrdProjectServiceImpl.class);
	private ICrdProductService crdProductService = SpringUtil.getBean(CrdProductServiceImpl.class);
	private ITbCrdApproveService crdApproveService = SpringUtil.getBean(TbCrdApproveServiceImpl.class);
	private ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);

	@Override
	public Response run(ServiceBody request) {
		//Response是所有响应体的父类，需要创建子类对象进行封装。
		String msg = null;
		LoanProjectResponseDTO rs = new LoanProjectResponseDTO();

		if (request.getRequest() instanceof LoanProjectRequestDTO) {
			LoanProjectRequestDTO requestDTO = (LoanProjectRequestDTO) request.getRequest();
			rs.setProjectNum(requestDTO.getProjectNum());//项目协议号
			rs.setBizSence(requestDTO.getBizScene());//业务场景
			rs.setBizAction(requestDTO.getBizAction());//流程节点
			//本地客户信息检查
			CsmParty csmParty = csmPartyService.getById(requestDTO.getCustomerNum());
			if (csmParty == null) {
				//本地客户不存在，同步ECIF信息
//			  commonService.getResponse(response, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL, "客户不存在");
				//查询ECIF客户信息
				//本地客户处理
			}

			//额度管控检查
			//本地处理
			//1.登记tb_crd_project_event（信贷实时-项目协议流水）；
			CrdProjectEvent crdProjectEvent = new CrdProjectEvent();
			crdProjectEvent.setProjectNum(requestDTO.getProjectNum());
			crdProjectEvent.setProjectName(requestDTO.getProjectName());
			crdProjectEvent.setAgreementTerm(requestDTO.getAgreementTerm());
			crdProjectEvent.setAgreementTermUnit(requestDTO.getAgreementTermUnit());
			//crdProjectEvent.setAviAmt("");//可用金额

			//检查产品信息
			CrdProduct params = new CrdProduct();
			params.setCustType(JxrcbBizConstant.CUST_TYPE_GS);//客户类型
			params.setProjectType(requestDTO.getProjectType());//项目类型
			params.setCrdProductType(JxrcbBizConstant.CRD_TYPE_TD);
			Crd crd = crdProductService.selectCrd(params);
			if (crd == null) {
				msg = "没有找到对应的额度产品信息，请配置额度产品参数！，客户类型：" + params.getCustType() +
					",项目类型：" + params.getProjectType() + "额度类型：" + params.getCrdProductType();
				log.warn(msg);
				return commonService.getResponse(rs, JxrcbConstant.ESB_STATUS_FAIL, JxrcbConstant.ESB_CODE_FAIL_F10401, msg);
			}

//			//生成额度编号
//			String crdDetailNum = commonService.getCrdNum(requestDTO.getEcifCustNum(), requestDTO.getOrgNum(), crd.getCrdProductNum());
//			String crdMainNum = commonService.getCrdNum(requestDTO.getEcifCustNum(), requestDTO.getOrgNum(), crd.getSuperCrdNum());

//			crdProjectEvent.setCrdDetailNum(crdDetailNum);//额度三级编号
			crdProjectEvent.setCrdDetailPrd(crd.getCrdProductNum());//额度三级产品号
//			crdProjectEvent.setCrdMainNum(crdMainNum);//额度二级编号
			crdProjectEvent.setCrdMainPrd(crd.getSuperCrdNum());//额度二级产品号
			crdProjectEvent.setTranSeqSn(requestDTO.getTranSeqSn());//流水号-主键
			crdProjectEvent.setCurrencyCd(requestDTO.getCurrencyCd());
			crdProjectEvent.setCustomerNum(requestDTO.getCustomerNum());//客户编号
			crdProjectEvent.setLimitControlType(requestDTO.getLimitControlType());
			crdProjectEvent.setOpType(requestDTO.getOpType());
			crdProjectEvent.setOrgNum(requestDTO.getOrgNum());
			crdProjectEvent.setProjectNum(requestDTO.getProjectNum());
			crdProjectEvent.setProjectStatus(requestDTO.getProjectStatus());
			crdProjectEvent.setProjectType(requestDTO.getProjectType());
			crdProjectEvent.setTotalAmt(requestDTO.getTotalAmt());
			crdProjectEvent.setTranDate(requestDTO.getApplyDate());//交易时间
			crdProjectEvent.setTranSystem(JxrcbBizConstant.TRAN_SYSTEM_LOAN);//交易系统
			crdProjectEvent.setUserNum(requestDTO.getUserNum());
			crdProjectEvent.setCreateTime(CommonUtil.getWorkDateTime());
			crdProjectEvent.setUpdateTime(CommonUtil.getWorkDateTime());

			crdProjectEventService.saveOrUpdate(crdProjectEvent);


			//2.登记tb_crd_project（项目协议表）：根据操作类型（01 新增02 调整），新增则新增记录，调整则修改记录，删除则修改批复状态，二者通过agreement_num进行关联；
			CrdProject crdProject = new CrdProject();
			crdProject.setProjectNum(crdProjectEvent.getProjectNum());
			crdProject.setProjectName(crdProjectEvent.getProjectName());
			crdProject.setAgreementTerm(crdProjectEvent.getAgreementTerm());
			crdProject.setAgreementTermUnit(crdProjectEvent.getAgreementTermUnit());
			crdProject.setAviAmt(crdProjectEvent.getAviAmt());
			crdProject.setUsedAmt(crdProjectEvent.getUsedAmt());
//			crdProject.setCrdDetailNum(crdProjectEvent.getCrdDetailNum());
			crdProject.setCrdDetailPrd(crdProjectEvent.getCrdDetailPrd());
//			crdProject.setCrdMainNum(crdProjectEvent.getCrdMainNum());
			crdProject.setCrdMainPrd(crdProjectEvent.getCrdMainPrd());
			crdProject.setCurrencyCd(crdProjectEvent.getCurrencyCd());
			crdProject.setCustomerNum(crdProjectEvent.getCustomerNum());
			crdProject.setLimitControlType(crdProjectEvent.getLimitControlType());
			crdProject.setOrgNum(crdProjectEvent.getOrgNum());

			crdProject.setProjectStatus(crdProjectEvent.getProjectStatus());//项目状态
			crdProject.setProjectType(crdProjectEvent.getProjectType());
			crdProject.setTotalAmt(crdProjectEvent.getTotalAmt());
			crdProject.setTranDate(crdProjectEvent.getTranDate());
			crdProject.setTranSystem(crdProjectEvent.getTranSystem());
			crdProject.setUserNum(crdProjectEvent.getUserNum());

			if (JxrcbBizConstant.OP_TYPE_ADD.equals(crdProjectEvent.getOpType())) {
				crdProject.setCreateTime(CommonUtil.getWorkDateTime());
			} else {
				crdProject.setUpdateTime(CommonUtil.getWorkDateTime());
			}
			crdProjectService.saveOrUpdate(crdProject);

			//3.执行额度重算，第三方额度重算
			msg = commonService.thirdRecount(requestDTO.getCustomerNum());
			Assert.isNull(msg, msg);

			//4.执行额度统计；
			msg = commonService.creditStatisCsm(requestDTO.getCustomerNum());
			Assert.isNull(msg, msg);

			msg = commonService.creditStatis();
			Assert.isNull(msg, msg);
		}
		return commonService.getResponse(rs, JxrcbConstant.ESB_STATUS_COMPLETE, JxrcbConstant.ESB_CODE_SUCCESS, "处理成功");
	}

}
