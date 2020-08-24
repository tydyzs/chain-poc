package org.git.modules.clm.front.dto.jxrcb.bank;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *  S00870000259003同业实时-明细额度详细信息查询(响应报文response)
 *  shc
 */
@Data
@XStreamAlias("response")
public class BankCreditDetailResponseDTO extends Response {

	@Length(max = 40)
	@XStreamAlias("ecif_cust_num")
	private String ecifCustNum;//ECIF客户号

	@NotBlank
	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@Valid
	@XStreamAlias("province_crd_main")
	private CrdMainInfoResponseDTO provinceCrdMain;//二级额度-省联社

	@Valid
	@XStreamAlias("crd_main_infos")
	private List<CrdMainInfoResponseDTO> crdMainInfo;//二级额度数组-成员行

	@Valid
	@XStreamAlias("province_crd_detail")
	private CrdDetailInfoResponseDTO provinceCrdDetail;//三级额度-省联社


	@Valid
	@XStreamAlias("crd_detail_infos")
	private List<CrdDetailInfoResponseDTO> crdDetailInfo;//额度明细数组-成员行

}
