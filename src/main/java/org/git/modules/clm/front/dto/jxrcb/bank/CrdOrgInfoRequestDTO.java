package org.git.modules.clm.front.dto.jxrcb.bank;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *  S00870000259003同业实时-明细额度详细信息查询(请求报文request)
 * crd_org_info(机构数组)
 * shc
 */
@Data
@XStreamAlias("item")
public class CrdOrgInfoRequestDTO {

	@NotBlank
	@Length(max = 24)
	@XStreamAlias("crd_grant_org_num")
	private String crdGrantOrgNum;//授信机构
}
