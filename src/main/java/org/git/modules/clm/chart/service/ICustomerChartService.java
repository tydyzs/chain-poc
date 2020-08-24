package org.git.modules.clm.chart.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.chart.vo.GrtVO;
import org.git.modules.system.entity.Dept;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;

import java.util.List;

public interface ICustomerChartService extends IService<T> {

	List<Dept> listOrgByCust(String customerNum);

	IPage<CrdQueryVO> listCrdSumPage(IPage<CrdQueryVO> page, CrdQueryVO crdQueryVO);

	IPage<CrdQueryVO> getCrdListSumPage(IPage<CrdQueryVO> page, CrdQueryVO crdQueryVO,String customerType);

	/**
	 * 同业客户额度列表分页
	 * @param page
	 * @param crdQueryVO
	 * @param customerType
	 * @return
	 */
	IPage<CrdQueryVO> queryLedgerCrdSumPage(IPage<CrdQueryVO> page, CrdQueryVO crdQueryVO,String customerType);

	List<CrdQueryVO> listCrdDetail(CrdQueryVO crdQueryVO);

	List<CrdQueryVO> listGuaranteeCrd(CrdQueryVO crdQueryVO);

	IPage<GrtVO> listGrtPage(IPage<GrtVO> page, String customerNum, String orgNum);

	TbCrdStatisCsm sumCsmCrdInfo(TbCrdStatisCsm tbCrdStatisCsm);
}
