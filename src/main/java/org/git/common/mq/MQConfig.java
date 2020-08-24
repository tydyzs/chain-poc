package org.git.common.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.activemq.ActiveMQConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * MQ配置类
 * @author caohaijie
 * @date 2018/12/12
 * @version 1.0
 * @since 1.8
 */
@Data
public class MQConfig {

	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String host = "localhost";
	private int port = 1414;//端口号
	private String channel;//通道
	private String manager;//队列管理器
	private String sendQueue;//发送队列名
	private String receiveQueue;//接收队列名

	private int maxConnections = 10;//连接池最大连接数
	private int idleTimeout;//空闲的连接过期时间，默认为30秒
	private int waitTimeout = 1000;//消息等待超时时间
	private int expiryTimeout = 0;//强制的连接过期时间，不管当前连接的情况，只要达到指定时间就失效。默认为0，never
	private int concurrentConsumer = 10;//并发的消费者数量


}


