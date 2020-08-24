/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.common.config;


import lombok.AllArgsConstructor;
import org.git.common.interceptor.LoginInterceptor;
import org.git.core.secure.handler.ISecureHandler;
import org.git.core.secure.props.ChainSecureProperties;
import org.git.core.secure.props.ChainSecureUrlProperties;
import org.git.core.secure.registry.SecureRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Chain配置
 *
 * @author Chill
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({ChainSecureUrlProperties.class, ChainSecureProperties.class,ClmConfiguration.class})
public class GitConfiguration implements WebMvcConfigurer {

	private final ChainSecureUrlProperties secureUrlProperties;

	private final ChainSecureProperties secureProperties;

	private final ISecureHandler secureHandler;

	private final ClmConfiguration clmConfiguration;

	@Bean
	public SecureRegistry secureRegistry() {
		SecureRegistry secureRegistry = new SecureRegistry();
		secureRegistry.setEnable(true);
		secureRegistry.excludePathPatterns("/git-doc/excel-report/download/bankCreditTable");
		secureRegistry.excludePathPatterns("/git-auth/oauth/token");
		secureRegistry.excludePathPatterns("/git-auth/oauth/depts");
		secureRegistry.excludePathPatterns("/git-log/**");
		secureRegistry.excludePathPatterns("/git-system/menu/routes");
		secureRegistry.excludePathPatterns("/git-system/menu/auth-routes");
		secureRegistry.excludePathPatterns("/git-system/menu/top-menu");
		secureRegistry.excludePathPatterns("/git-flow/process/resource-view");
		secureRegistry.excludePathPatterns("/git-flow/process/diagram-view");
		secureRegistry.excludePathPatterns("/git-flow/manager/**");
		secureRegistry.excludePathPatterns("/doc.html");
		secureRegistry.excludePathPatterns("/js/**");
		secureRegistry.excludePathPatterns("/webjars/**");
		secureRegistry.excludePathPatterns("/swagger-resources/**");
		secureRegistry.excludePathPatterns("/druid/**");
		secureRegistry.excludePathPatterns("/git-doc/preview");
		secureRegistry.excludePathPatterns("/git-doc/download");
		secureRegistry.excludePathPatterns("/index.html");
		return secureRegistry;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/cors/**")
			.allowedOrigins("*")
			.allowedHeaders("*")
			.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
			.maxAge(3600)
			.allowCredentials(true);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		secureProperties.getClient().forEach(
			cs -> registry.addInterceptor(secureHandler.clientInterceptor(cs.getClientId()))
				.addPathPatterns(cs.getPathPatterns())
		);
		SecureRegistry secureRegistry = secureRegistry();
		if (secureRegistry.isEnable()) {
			registry.addInterceptor(new LoginInterceptor(clmConfiguration))
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureUrlProperties.getExcludePatterns())
				.excludePathPatterns(secureProperties.getSkipUrl());
		}
	}
}
