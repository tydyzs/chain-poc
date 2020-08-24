package org.git.modules.clm.rcm.service;

import lombok.AllArgsConstructor;
import org.git.modules.clm.rcm.entity.RcmConfigTotal;
import org.git.modules.clm.rcm.mapper.RcmLedgerMapper;
import org.git.modules.clm.rcm.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Chain-Boot
 * 限额台账
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/1/3
 * @since 1.8
 */
@AllArgsConstructor
@Service
public class RcmLedgerService {
	private RcmLedgerMapper rcmLedgerMapper;

	public RcmQuotaManagerVO queryQuotaDetail(String quotaNum) {
		return rcmLedgerMapper.queryQuotaDetail(quotaNum);
	}

	public List<RcmLedgerQuotaVO> queryHistoryTotal(String quotaNum) {
		return rcmLedgerMapper.queryHistoryTotal(quotaNum);
	}

	public List<RcmLedgerWarnVO> queryHistoryWarn(String quotaNum) {
		return rcmLedgerMapper.queryHistoryWarn(quotaNum);
	}

	public List<RcmConfigTotalVO> queryCurrentTotal(String quotaNum) {
		return rcmLedgerMapper.queryCurrentTotal(quotaNum,null);
	}



}
