package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import org.git.common.annotation.ValidatorDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * rel_people_info_query(关系人信息组)子项
 */
@Data
public class RelPeopleInfo {
	@XStreamImplicit(itemFieldName = "item")
	private List<RelPeopleInfoResponseDTO> relPeopleInfoQuery;
}
