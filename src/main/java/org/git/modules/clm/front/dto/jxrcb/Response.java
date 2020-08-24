package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Data;

@Data
@XStreamAlias("response")
public class Response {

	/**
	 * 服务响应处理结果
	 */
	@XStreamOmitField
	private ServiceResponse serviceResponse;

}
