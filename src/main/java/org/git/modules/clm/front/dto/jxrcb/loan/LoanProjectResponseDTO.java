package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CLM104_信贷系统-项目协议同步(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanProjectResponseDTO extends Response {

	@Length(max = 50)
	@XStreamAlias("project_num")
	private String projectNum;//项目协议编号

	@Length(max = 2)
	@XStreamAlias("biz_scene")
	private String bizSence;//业务场景

	@Length(max = 2)
	@XStreamAlias("biz_action")
	private String bizAction;//流程节点
}
