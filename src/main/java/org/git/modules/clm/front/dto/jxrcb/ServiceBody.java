package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import javax.validation.Valid;

@Data
@XStreamAlias("Service_Body")
public class ServiceBody {

	@Valid
	@XStreamAlias("ext_attributes")
	private ExtAttributes extAttributes;

	@Valid
	@XStreamAlias("request")
	private Request request;

	@XStreamAlias("response")
	private Response response;
}
