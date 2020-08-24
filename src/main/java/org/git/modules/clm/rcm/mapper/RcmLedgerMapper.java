package org.git.modules.clm.rcm.mapper;

import org.git.modules.clm.rcm.entity.RcmConfigTotal;
import org.git.modules.clm.rcm.entity.RcmWarnInfo;
import org.git.modules.clm.rcm.vo.*;

import java.util.List;

/**
 * 限额台账
 */
public interface RcmLedgerMapper {

	/**
	 * 查询限额控制参数
	 *
	 * @param quotaNum
	 * @return
	 */
	RcmQuotaManagerVO queryQuotaDetail(String quotaNum);

	/**
	 * 查询限额历史统计
	 *
	 * @param quotaNum
	 * @return
	 */
	List<RcmLedgerQuotaVO> queryHistoryTotal(String quotaNum);

	/**
	 * 查询当前限额统计
	 *
	 * @param quotaNum
	 * @return
	 */
	List<RcmLedgerWarnVO> queryHistoryWarn(String quotaNum);


	/**
	 * 查询当前限额统计
	 *
	 * @param quotaNum
	 * @return
	 */
	List<RcmConfigTotalVO> queryCurrentTotal(String quotaNum,String quotaIndexNum);




}
