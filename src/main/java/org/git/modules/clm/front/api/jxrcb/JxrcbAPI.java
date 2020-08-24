package org.git.modules.clm.front.api.jxrcb;

import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.common.service.ICommonService;
import org.git.modules.clm.common.service.impl.CommonServiceImpl;
import org.git.modules.clm.front.api.ThirdOpenAPI;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceBody;

/**
 * 江西农信接口-统一业务处理
 * 子类需要配置 tb_service_config.invode_api字段
 */
public abstract class JxrcbAPI implements ThirdOpenAPI {

	protected ICommonService commonService = SpringUtil.getBean(CommonServiceImpl.class);


	/**
	 * 开始执行
	 *
	 * @param serviceBody 请求报文体
	 * @return
	 */
	public abstract Response run(ServiceBody serviceBody);

}
