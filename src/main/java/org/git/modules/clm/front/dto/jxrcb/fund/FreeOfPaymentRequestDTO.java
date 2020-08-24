package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.annotation.ValidatorDict;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CLM207-资金-纯券转让(请求报文request)
 */
@Data
@XStreamAlias("request")
public class FreeOfPaymentRequestDTO extends Request {

	@NotBlank
	@Length(max = 40)
	@XStreamAlias("busi_deal_num")
	private String busiDealNum;//业务编号

	@NotBlank
	@Length(max = 2)
	@XStreamAlias("tran_type_cd")
	private String tranTypeCd;//交易类型

	@NotBlank
	@Length(max = 2)
	@ValidatorDict(code="CD000270")
	@XStreamAlias("tran_direction")
	private String tranDirection;//交易方向

	@Valid
	@NotNull
	@XStreamAlias("bond_tran_infos")
	private List<BondTranInfoRequestDTO> bondTranInfo;//纯券转让数组（

}
