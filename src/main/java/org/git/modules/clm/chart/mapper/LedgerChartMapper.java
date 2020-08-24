package org.git.modules.clm.chart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.loan.vo.CrdDetailVO;

import java.util.List;

public interface LedgerChartMapper extends BaseMapper<T> {

	/**
	 * 查询同业额度视图基本信息
	 * @param crdQueryVO
	 * @return
	 */
	List<CrdQueryVO> ledgerDetail(CrdQueryVO crdQueryVO);

	/**
	 * 额度结构 查询机构为省联社的明细
	 * @return
	 * @param crdQueryVO
	 */
	List<CrdQueryVO> queryLedgerDeatilByOrgType(CrdQueryVO crdQueryVO);

	/**
	 * 额度结构 查询机构为成员行的明细
	 * @return
	 */
	List<CrdQueryVO> queryLedgerDeatilByOrgTypeThree(CrdQueryVO crdQueryVO);
}
