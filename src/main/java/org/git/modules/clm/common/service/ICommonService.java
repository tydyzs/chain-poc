package org.git.modules.clm.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.customer.entity.CsmParty;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;
import org.git.modules.clm.rcm.vo.RcmQuotaAnalysisRptQueryVO;
import org.git.modules.desk.vo.NoticeVO;

/**
 * 公共服务类
 */
public interface ICommonService extends IService<T> {

	/**
	 * 获取额度明细表数据对象
	 *
	 * @param customerNum  客户编号
	 * @param orgNum       机构编号
	 * @param crdDetailPrd 额度产品编号
	 * @return
	 */
	CrdDetail getOneCrdDetail(String customerNum, String orgNum, String crdDetailPrd);

	/**
	 * 获取额度主表数据对象
	 *
	 * @param customerNum 客户编号
	 * @param orgNum      机构编号
	 * @param crdMainPrd  额度产品编号
	 * @return CrdMain 额度主表信息
	 */
	CrdMain getOneCrdMain(String customerNum, String orgNum, String crdMainPrd);

	/**
	 * 根据机构号，获取所在成员行
	 *
	 * @param orgNum
	 * @return
	 */
	String getCorpOrgCode(String orgNum);


	/**
	 * 获取额度编号，如果系统已有，则返回，如果系统没有，返回一个自动生成的编号
	 *
	 * @param customerNum 客户号
	 * @param orgNum      机构号
	 * @param crdPrd      额度品种
	 * @return
	 */
	public String getCrdNum(String customerNum, String orgNum, String crdPrd);


	/**
	 * 根据返回对象、状态码、错误码、描述信息返回Response对象
	 *
	 * @param response
	 * @param status
	 * @param code
	 * @param Desc
	 * @return
	 */
	Response getResponse(Response response, String status, int code, String Desc);


	/**
	 * 授信额度重算
	 *
	 * @param customerNum
	 */
	String creditRecount(String customerNum);

	/**
	 * 第三方额度重算
	 *
	 * @param customerNum
	 */
	String thirdRecount(String customerNum);

	/**
	 * 担保额度重算
	 *
	 * @param customerNum
	 */
	String guaranteeRecount(String customerNum);

	/**
	 * 客户额度统计
	 *
	 * @param customerNum
	 * @return
	 */
	String creditStatisCsm(String customerNum);

	/**
	 * 额度统计
	 *
	 * @param
	 * @return
	 */
	String creditStatis();


	/**
	 * 历史额度统计
	 *
	 * @param
	 * @return
	 */
	String creditStatisHs();

	/**
	 * 额度重算并且进行统计
	 *
	 * @param customerNum
	 * @return
	 */
	String creditRecountAStatis(String customerNum);

	/**
	 * 限额分析-查询条件对象赋值
	 *
	 * @param queryVO 查询条件对象
	 */
	void setQueryRequirement(RcmQuotaAnalysisRptQueryVO queryVO);

	/**
	 * 根据客户编号和客户类型获取客户信息，如果本地存在，返回本地数据，本地不存在去ECIF同步
	 *
	 * @param customerNum
	 * @param customerType(1个人客户 2 公司客户 3 同业客户)
	 * @return CsmParty
	 */
	CsmParty getCsmParty(String customerNum, String... customerType);


	/**
	 * 自定义分页
	 * @param page
	 * @param noticeVO
	 * @return
	 */
	IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO noticeVO);



}
