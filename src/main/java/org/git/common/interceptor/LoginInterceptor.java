package org.git.common.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.git.common.cache.LoginCache;
import org.git.common.config.ClmConfiguration;
import org.git.core.secure.ChainUser;
import org.git.core.secure.utils.SecureUtil;
import org.git.core.tool.api.R;
import org.git.core.tool.api.ResultCode;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.jackson.JsonUtil;
import org.git.core.tool.utils.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private ClmConfiguration clmConfiguration;

	public LoginInterceptor() {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		ChainUser chainUser = SecureUtil.getUser();
		R result = null;
		if(null == chainUser || StringUtils.isBlank(chainUser.getAccount())){
			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			result = R.fail(ResultCode.UN_AUTHORIZED);
		}else if(LoginCache.getLoginUser(chainUser.getAccount()) == null){
			log.warn("用户未登录，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			result = R.fail(ResultCode.UN_AUTHORIZED);
		}else{
			//验证通过后更新缓存时间默认30分钟
			LoginCache.loginExpire(chainUser.getAccount(),clmConfiguration.getLoginExpire());
			return true;
		}
		response.setCharacterEncoding(ChainConstant.UTF_8);
		response.setHeader(ChainConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		return false;
	}
}
