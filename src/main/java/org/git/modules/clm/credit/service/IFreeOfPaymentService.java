package org.git.modules.clm.credit.service;

import org.git.modules.clm.credit.dto.FreeOfPaymentEventVO;
import org.git.modules.clm.credit.entity.CrdGrantingSerial;
import org.git.modules.clm.credit.entity.ParCrdRuleCtrl;
import org.git.modules.clm.front.dto.jxrcb.ExtAttributes;
import org.git.modules.clm.front.dto.jxrcb.fund.CertInfoRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.fund.FreeOfPaymentRequestDTO;
import org.git.modules.clm.loan.entity.CrdApplySerial;
import org.git.modules.clm.loan.entity.CrdBusiCertInfo;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.clm.loan.entity.CrdMain;

import java.util.List;

/**
 * @author liuy
 */
public interface IFreeOfPaymentService {
	/**
	 * 事件落地
	 * @param freeOfPaymentRequestDTO
	 * @param extAttributes
	 */
	FreeOfPaymentEventVO registerEvent(FreeOfPaymentRequestDTO freeOfPaymentRequestDTO, ExtAttributes extAttributes);

	/**
	 * 事件处理
	 * @param freeOfPaymentEventVO
	 */
	void middleHandle(FreeOfPaymentEventVO freeOfPaymentEventVO);
}
