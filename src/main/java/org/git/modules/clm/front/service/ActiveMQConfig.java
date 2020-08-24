package org.git.modules.clm.front.service;

import lombok.Data;
import org.apache.activemq.ActiveMQConnection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMQConfig {

	private boolean enable = Boolean.FALSE;//是否启用

	private String user = ActiveMQConnection.DEFAULT_USER;

	private String password = ActiveMQConnection.DEFAULT_PASSWORD;

	private String brokerUrl = "tcp://localhost:61616";

	private int maxConnections = 10;//连接池最大连接数

	private int idleTimeout;//空闲的连接过期时间，默认为30秒

	/**
	 * 强制的连接过期时间，与idleTimeout的区别在于：idleTimeout是在连接空闲一段时间失效，
	 * 而expiryTimeout不管当前连接的情况，只要达到指定时间就失效。默认为0，never
	 */
	private int expiryTimeout = 0;

	private int concurrentConsumers = 10;

	public static String REPLY_QUEUE_NAME = "activemq.reply";



}
