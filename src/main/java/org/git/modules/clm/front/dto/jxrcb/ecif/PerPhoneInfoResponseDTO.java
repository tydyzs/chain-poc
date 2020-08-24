package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_phone_info_query(联系电话信息组)子项
 */
@Data
@XStreamAlias("item")
public class PerPhoneInfoResponseDTO {
	@Length(max = 20)
	@XStreamAlias("phone_id")
	private String phoneId;//电话ID

	@Length(max = 3)
	@XStreamAlias("conn_type")
	private String connType;//联系类型 CD000007

	@Length(max = 3)
	@XStreamAlias("inter_code")
	private String interCode;//国际长途区号

	@Length(max = 4)
	@XStreamAlias("inland_code")
	private String inlandCode;//国内长途区号

	@Length(max = 13)
	@XStreamAlias("tel_number")
	private String telNumber;//联系号码

	@Length(max = 256)
	@XStreamAlias("exten_num")
	private String extenNum;//分机号

	@Length(max = 1)
	@XStreamAlias("is_check_flag")
	private String isCheckFlag;//是否已核实
}
