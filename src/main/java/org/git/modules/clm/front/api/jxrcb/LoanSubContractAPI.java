package org.git.modules.clm.front.api.jxrcb;

import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;

/**
 * 江西农信信贷系统-担保合同处理接口
 */
public class LoanSubContractAPI extends JxrcbAPI {

	@Override
	public Response run(ServiceBody request) {

		//Response是所有响应体的父类，需要创建子类对象进行封装。
		Response response = new Response();
		return response;
	}
}
