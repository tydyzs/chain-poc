package org.git.modules.clm.front.dto.jxrcb.ecif;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
/**
 * ECIF_00880000420102-对私客户综合信息查询(响应报文response)
 * per_addr_info_query(联系地址信息组)子项
 */
@Data
@XStreamAlias("item")
public class PerAddrInfoResponseDTO {

	@Length(max = 20)
	@XStreamAlias("addr_id")
	private String addrId;//物理地址ID

	@Length(max = 3)
	@XStreamAlias("conn_type")
	private String connType;//联系类型 CD000007

	@Length(max = 3)
	@XStreamAlias("coun_regi")
	private String counRegi;//联系地址国家/地区 CD000005

	@Length(max = 6)
	@XStreamAlias("province")
	private String province;//省代码 CD000006

	@Length(max = 6)
	@XStreamAlias("city")
	private String city;//市代码 CD000006

	@Length(max = 6)
	@XStreamAlias("county")
	private String county;//县代码 CD000006

	@Length(max = 60)
	@XStreamAlias("street")
	private String street;//街道地址

	@Length(max = 256)
	@XStreamAlias("detail_addr")
	private String detailAddr;//详细地址

	@Length(max = 256)
	@XStreamAlias("eng_addr")
	private String engAddr;//英文地址

	@Length(max = 6)
	@XStreamAlias("post_code")
	private String postCode;//邮政编码
}
