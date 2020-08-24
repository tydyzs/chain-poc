package org.git.modules.clm.front.dto.jxrcb.bill;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.loan.QuotaInfoResponseDTO;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * CLM302_票据实时-直贴、转帖用信交易(响应报文response)
 */
@Data
@XStreamAlias("response")
public class BillDiscountResponseDTO extends Response {

	@Length(max = 40)
	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号

	@Length(max = 2)
	@XStreamAlias("tran_status")
	private String tranStatus;//处理状态

	@Length(max = 200)
	@XStreamAlias("tran_desc")
	private String tranDesc;//返回信息

	@XStreamAlias("quota_info")
	private List<QuotaInfoResponseDTO> quotaInfo;//限额预警信息数组
}
