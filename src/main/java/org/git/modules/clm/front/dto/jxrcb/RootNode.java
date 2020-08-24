package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import javax.validation.Valid;

/**
 * 江西农信ESB报文根节点
 */
@Data
@XStreamAlias("Service")
public class RootNode {

	@Valid
	@XStreamAlias("Service_Header")
	ServiceHeader serviceHeader;

	@Valid
	@XStreamAlias("Service_Body")
	ServiceBody serviceBody;



}
