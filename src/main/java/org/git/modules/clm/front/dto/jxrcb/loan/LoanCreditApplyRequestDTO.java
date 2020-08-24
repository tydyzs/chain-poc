package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorAmt;
import org.git.common.annotation.ValidatorDate;
import org.git.common.annotation.ValidatorDict;
import org.git.common.constant.CommonConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * CLM101_信贷系统-额度申请(请求报文request)
 */
@Valid
@Data
@XStreamAlias("request")
public class LoanCreditApplyRequestDTO extends Request {

	@NotBlank
	@Length(max = 50)
	@XStreamAlias("tran_seq_sn")
	private String tranSeqSn;//交易流水号

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点

	@Valid
	@XStreamAlias("limit_detail_infos")
	private List<LimitDetailInfoRequestDTO> limitDetailInfo;//额度明细信息分组
}
