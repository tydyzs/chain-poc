package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_cert_info_query(证件信息分组)子项
 */
@Data
public class PerCertInfo {

	@XStreamImplicit(itemFieldName = "item")
	private List<PerCertInfoResponseDTO> perCertInfoQuery;
}
