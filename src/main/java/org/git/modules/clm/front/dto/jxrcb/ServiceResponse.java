package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@XStreamAlias("service_response")
public class ServiceResponse {

	public ServiceResponse() {
		this.status = JxrcbConstant.ESB_STATUS_COMPLETE;
		this.desc = "处理成功";
	}

	@NotBlank
	@XStreamAlias("status")
	private String status;//当前交易状态

	@XStreamAlias("code")
	private String code;//当前交易状态码

	@XStreamAlias("desc")
	private String desc;//当前状态描述

	@XStreamAlias("requester_code")
	private String requesterCode;//选填

	@XStreamAlias("requester_desc")
	private String requesterDesc;//选填

	@XStreamAlias("catalog")
	private String catalog;

}
