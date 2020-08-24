package org.git.modules.clm.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.SpringUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.common.mapper.CommonMapper;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.customer.service.ICsmPartyService;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceResponse;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.loan.service.ICrdDetailService;
import org.git.modules.clm.loan.service.ICrdMainService;
import org.git.modules.clm.loan.service.impl.CrdDetailServiceImpl;
import org.git.modules.clm.loan.service.impl.CrdMainServiceImpl;
import org.git.modules.clm.rcm.constant.RcmConstant;
import org.git.modules.clm.rcm.vo.RcmQuotaAnalysisRptQueryVO;
import org.git.modules.desk.vo.NoticeVO;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.service.IDeptService;
import org.git.modules.system.service.impl.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公共服务实现类
 */
@Service
@Transactional
public class CommonServiceImpl extends ServiceImpl<CommonMapper, T> implements ICommonService {


	@Autowired
	ICsmPartyService iCsmPartyService;

	/**
	 * 获取额度明细表数据对象
	 *
	 * @param customerNum   客户编号
	 * @param orgNum        机构编号
	 * @param crdProductNum 额度产品编号
	 * @return
	 */
	@Override
	public CrdDetail getOneCrdDetail(String customerNum, String orgNum, String crdProductNum) {
		System.out.println("查询参数，客户号：" + customerNum + "，机构号：" + orgNum + "，额度品种编号：" + crdProductNum);
		//查询机构信息
		String corpOrgCode = getCorpOrgCode(orgNum);

		ICrdDetailService crdDetailService = SpringUtil.getBean(CrdDetailServiceImpl.class);
		CrdDetail crdDetail = new CrdDetail();
		crdDetail.setCustomerNum(customerNum);
		crdDetail.setOrgNum(corpOrgCode);//成员行机构
		crdDetail.setCrdDetailPrd(crdProductNum);
		CrdDetail oneCrdDetail = crdDetailService.getOne(Condition.getQueryWrapper(crdDetail));
		return oneCrdDetail;
	}

	/**
	 * 获取额度明细表数据对象
	 *
	 * @param customerNum 客户编号
	 * @param orgNum      机构编号
	 * @param crdMainPrd  二级额度产品
	 * @return
	 */
	@Override
	public CrdMain getOneCrdMain(String customerNum, String orgNum, String crdMainPrd) {
		System.out.println("查询参数，客户号：" + customerNum + "，机构号：" + orgNum + "，额度品种编号：" + crdMainPrd);
		//查询机构信息
		String corpOrgCode = getCorpOrgCode(orgNum);

		ICrdMainService crdMainService = SpringUtil.getBean(CrdMainServiceImpl.class);
		CrdMain crdMain = new CrdMain();
		crdMain.setCustomerNum(customerNum);
		crdMain.setOrgNum(corpOrgCode);//成员行机构
		crdMain.setCrdMainPrd(crdMainPrd);
		CrdMain oneCrdMain = crdMainService.getOne(Condition.getQueryWrapper(crdMain));
		return oneCrdMain;
	}

	/**
	 * 根据返回对象、状态码、错误码、描述信息返回Response对象
	 *
	 * @param response
	 * @param status
	 * @param code
	 * @param Desc
	 * @return
	 */
	@Override
	public Response getResponse(Response response, String status, int code, String Desc) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setDesc(Desc);
		serviceResponse.setStatus(status);
		serviceResponse.setCode(String.valueOf(code));
		if (response == null) {
			response = new Response();
		}
		response.setServiceResponse(serviceResponse);
		return response;
	}

	/**
	 * 根据机构号，获取所在成银行
	 *
	 * @param orgNum
	 * @return
	 */
	@Override
	public String getCorpOrgCode(String orgNum) {
		//查询机构信息
		IDeptService iDeptService = SpringUtil.getBean(DeptServiceImpl.class);
		Dept dept = new Dept();
		dept.setId(orgNum);
		dept = iDeptService.getOne(Condition.getQueryWrapper(dept));
		if (dept == null) {
			return null;
		}
		return dept.getCorpOrgCode();
	}

	/**
	 * @param customerNum 客户号
	 * @param orgNum      机构号
	 * @param crdPrd      额度品种
	 * @return
	 */
	@Override
	public String getCrdNum(String customerNum, String orgNum, String crdPrd) {
		if (StringUtil.isBlank(crdPrd)) {
			return null;
		}
		if (crdPrd.length() == 8) {//三级额度品种
			//获取额度明细信息
			CrdDetail crdDetail = getOneCrdDetail(customerNum, orgNum, crdPrd);
			if (crdDetail == null || StringUtil.isBlank(crdDetail.getCrdDetailNum())) {//如果没有对应的额度信息，需要生成新的额度编号
				System.out.println("未获取到额度已存在的三级额度编号，将重新生成");
				return BizNumUtil.getBizNumWithDate(JxrcbBizConstant.THIRD_CREDIT);//三级额度编号
			} else {
				System.out.println("获取到额度已存在的三级额度编号：" + crdDetail.getCrdDetailNum());
				return crdDetail.getCrdDetailNum();
			}
		} else if (crdPrd.length() == 4) {//二级额度品种
			//获取额度主信息
			CrdMain crdMain = getOneCrdMain(customerNum, orgNum, crdPrd);
			if (crdMain == null || StringUtil.isBlank(crdMain.getCrdMainNum())) {//如果没有对应的额度信息，需要生成新的额度编号
				System.out.println("未获取到额度已存在的二级额度编号，将重新生成");
				return BizNumUtil.getBizNumWithDate(JxrcbBizConstant.SECOND_CREDIT);//二级额度编号
			} else {
				System.out.println("获取到额度已存在的二级额度编号：" + crdMain.getCrdMainNum());
				return crdMain.getCrdMainNum();//二级额度编号
			}
		}
		return null;
	}


	@Override
	public String thirdRecount(String customerNum) {
		try {
			baseMapper.thirdRecount(customerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "第三方额度额度重算出现异常，客户号：" + customerNum;
		}
		return null;
	}

	@Override
	public String guaranteeRecount(String customerNum) {
		try {
			baseMapper.guaranteeRecount(customerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "担保额度重算出现异常，客户号：" + customerNum;
		}
		return null;
	}


	@Override
	public String creditRecount(String customerNum) {
		try {
			baseMapper.creditRecount(customerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "授信额度重算出现异常，客户号：" + customerNum;
		}
		return null;
	}

	@Override
	public String creditStatisCsm(String customerNum) {
		try {
			baseMapper.creditStatisCsm(customerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "额度统计出现异常，客户号：" + customerNum;
		}
		return null;
	}

	@Override
	public String creditStatis() {
		try {
		//	baseMapper.creditStatis();
		} catch (Exception e) {
			e.printStackTrace();
			return "额度统计出现异常";
		}
		return null;
	}

	@Override
	public String creditStatisHs() {
		try {
			baseMapper.creditStatisHs();
		} catch (Exception e) {
			e.printStackTrace();
			return "历史额度统计出现异常";
		}
		return null;
	}

	@Override
	public String creditRecountAStatis(String customerNum) {
		try {
			baseMapper.creditRecount(customerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "额度重算出现异常，客户编号：" + customerNum;
		}
		try {
			baseMapper.creditStatisCsm(customerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "客户额度统计出现异常，客户编号：" + customerNum;
		}
		try {
	//		baseMapper.creditStatis();
		} catch (Exception e) {
			e.printStackTrace();
			return "客户额度统计出现异常，客户编号：" + customerNum;
		}
		return null;
	}

	/**
	 * 限额分析-查询条件对象息赋值
	 *
	 * @param queryVO 查询条件对象
	 */
	@Override
	public void setQueryRequirement(RcmQuotaAnalysisRptQueryVO queryVO) {
		IDeptService deptService = SpringUtil.getBean(DeptServiceImpl.class);
		/*获取登录用户机构信息*/
		Dept dept = deptService.getById(CommonUtil.getCurrentOrgId());
		/*登录用户机构类型赋值*/
		queryVO.setUserOrgType(dept.getOrgType());
		/*登录用户机构号赋值*/
		queryVO.setUserOrgNum(CommonUtil.getCurrentOrgId());
		if (queryVO.getOrgNum() != null && !"".equals(queryVO.getOrgNum())) {
			/*若页面传入了机构,获取机构类型及法人机构*/
			dept = deptService.getById(queryVO.getOrgNum());
			if (dept != null) {
				/*机构类型赋值*/
				queryVO.setOrgType(dept.getOrgType());
				/*机构号赋值*/
				queryVO.setOrgNum(queryVO.getOrgNum());
			}
		}
		/*获取当前营业日期 yyyy-MM-dd*/
		String year = CommonUtil.getWorkYear();
		String month = CommonUtil.getWorkMonth();
		if (queryVO.getTotalYear() != null && !"".equals(queryVO.getTotalYear())) {
			/*若页面传入 年份*/
			year = queryVO.getTotalYear();
		}
		if (queryVO.getTotalMonth() != null && !"".equals(queryVO.getTotalMonth())) {
			/*若页面传入 月份*/
			month = queryVO.getTotalMonth();
			month = String.format("%2s", month).replace(' ', '0');//左填充0为2位
		}
		/*上月营业年份*/
		String lastMonthYear = year;
		/*上月营业月份*/
		String lastMonth = String.valueOf(Integer.parseInt(month) - 1);
		if ("0".equals(lastMonth)) {
			/*日期查询条件为本年一月份时，上月为去年十二月份*/
			lastMonth = "12";
			lastMonthYear = String.valueOf(Integer.parseInt(year) - 1);
		}
		if (lastMonth.length() == 1) {
			lastMonth = "0" + lastMonth;
		}
		queryVO.setTotalYear(year);
		queryVO.setTotalMonth(month);
		queryVO.setLastMonthYear(lastMonthYear);
		queryVO.setLastMonth(lastMonth);
		if (RcmConstant.AMT_UNIT_TEN_THOUSAND.equals(queryVO.getAmtUnit())) {
			queryVO.setAmtUnit("10000"); //单位万元
		} else if (RcmConstant.AMT_UNIT_ONE_HUNDRED_MILLION.equals(queryVO.getAmtUnit())) {
			queryVO.setAmtUnit("100000000"); //单位亿元
		}
	}

	/**
	 * 根据客户编号和客户类型获取客户信息，如果本地存在，返回本地数据，本地不存在去ECIF同步
	 *
	 * @param customerNum
	 * @param customerType
	 * @return CsmParty
	 */
	public CsmParty getCsmParty(String customerNum, String... customerType) {
		CsmParty party = iCsmPartyService.getById(customerNum);

		//本地查到客户信息
		if (party != null && !StringUtil.isEmpty(party.getCustomerNum())) {
			return party;
		}

		//TODO 调用ECIF获取客户信息
		//party = iecifCustomerInfoService.dealCustomerInfo(rq.getCustomerNum(), rq.getCustomerType());
		party = new CsmParty();
		return party;
	}

	/**
	 * 获取通知信息
	 * @param page
	 * @param noticeVO
	 * @return
	 */
	@Override
	public IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO noticeVO) {
		// 若不使用mybatis-plus自带的分页方法，则不会自动带入tenantId，所以我们需要自行注入
		//notice.setTenantId(SecureUtil.getTenantId());
		List<NoticeVO> list = baseMapper.selectNoticePage(page, noticeVO);
		return page.setRecords(list);
	}

}
