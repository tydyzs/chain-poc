package org.git.modules.clm.chart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdQueryVO;

import java.util.List;

public interface ILedgerChartService extends IService<T> {

	/**
	 * 查询同业额度视图基本信息
	 * @param crdQueryVO
	 * @return
	 */
	List<CrdQueryVO> ledgerDetail(CrdQueryVO crdQueryVO);

	/**
	 * 额度结构 查询机构为省联社的明细
	 * @return
	 */
	List<CrdQueryVO> queryLedgerDeatilByOrgType(String customerNum);

	/**
	 * 额度结构 查询机构为成员行的明细
	 * @return
	 */
	List<CrdQueryVO> queryLedgerDeatilByOrgTypeThree(String crdGrantOrgNum, String customerNum);
}
