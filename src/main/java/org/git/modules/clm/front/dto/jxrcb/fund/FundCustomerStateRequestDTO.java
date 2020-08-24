package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

/**
 * CLM203_资金实时-资金客户状态维护(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FundCustomerStateRequestDTO extends Request {


	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务申请编号

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@Length(max = 40)
	@XStreamAlias("crd_main_prd")
	private String crdMainPrd;//二级产品编号

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("customer_num")
	private String customerNum;//ecif客户编号

	@Length(max = 10)
	@XStreamAlias("crd_status")
	private String crdStatus;//额度状态

	@XStreamAlias("frozen_req_date")
	private String frozenReqDate;//冻结申请

	@XStreamAlias("frozen_begin_date")
	private String frozenBeginDate;//冻结起期

	@XStreamAlias("frozen_over_date")
	private String frozenOverDate;//冻结止期

	@XStreamAlias("crd_admit_info")
	private List<CrdAdmitInfoRequestDTO> crdAdmitInfo;//数组
}
