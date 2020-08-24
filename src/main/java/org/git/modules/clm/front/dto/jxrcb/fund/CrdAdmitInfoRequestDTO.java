package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * CLM203_资金实时-资金客户状态维护(请求报文request)
 * crd_admit_info(数组)
 */
@Data
@XStreamAlias("item")
public class CrdAdmitInfoRequestDTO {

	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@Length(max = 10)
	@XStreamAlias("crd_admit_flag")
	private String crdAdmitFlag;//客户准入
}
