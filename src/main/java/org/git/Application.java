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
package org.git;

import org.git.common.constant.CommonConstant;
import org.git.core.launch.ChainApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动器
 *
 * @author Chill
 */
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"org.git.**.mapper"})
//jar包启动
public class Application {

	public static void main(String[] args) {
		ChainApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);
	}

}
//war包启动
//public class Application extends SpringBootServletInitializer {
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		ChainApplication.createSpringApplicationBuilder(CommonConstant.APPLICATION_NAME, Application.class);
//		return builder.sources(Application.class);
//	}
//
//}
