package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;


/**
 * CLM107_信贷系统-押品信息同步(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanGuarantyInfoResponseDTO extends Response {

	@Length(max = 50)
	@XStreamAlias("surety_num")
	private String suretyNum;//1.押品编号

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizScene;//业务场景

	//	@NotBlank
//	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点
}
