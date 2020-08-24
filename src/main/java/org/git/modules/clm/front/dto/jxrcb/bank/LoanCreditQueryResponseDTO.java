package org.git.modules.clm.front.dto.jxrcb.bank;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *  S00870000259004 票据额度查询(响应报文response)
 *  shc
 */
@Data
@XStreamAlias("response")
public class LoanCreditQueryResponseDTO extends Response {

	@Length(max = 40)
	@XStreamAlias("ecif_cust_num")
	private String ecifCustNum;//ECIF客户号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@Valid
	@XStreamAlias("this_crd_main_info")
	private CrdMainInfoResponseDTO thisCrdMain;//二级额度-省联社

	@Valid
	@XStreamAlias("province_crd_main")
	private CrdMainInfoResponseDTO provinceCrdMain;//二级额度-省联社

	@Valid
	@XStreamAlias("crd_main_infos")
	private List<CrdMainInfoResponseDTO> crdMainInfos;//二级额度数组-成员行

}
