package org.git.common.mq;


import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * MQ连接工厂
 * @author caohaijie
 * @version 1.0
 * @date 2019/12/11
 * @since 1.8
 */
public class ConnectionHelper {


	@Getter
	@Setter
	private MQConfig mqConfig;//IBM MQ配置

	public ConnectionHelper() {

	}

	public ConnectionHelper(MQConfig config) {
		this.mqConfig = config;
	}

	/**
	 * 创建IBMMQ连接工厂
	 *
	 * @return
	 * @throws JMSException
	 */
	public synchronized ConnectionFactory createConnectionFactory(MQConfig mqConfig) throws JMSException {
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory jmsConnectionFactory = ff.createConnectionFactory();
		jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, mqConfig.getHost());
		jmsConnectionFactory.setIntProperty(WMQConstants.WMQ_PORT, mqConfig.getPort());
		jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, mqConfig.getChannel());
		jmsConnectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, mqConfig.getManager());
		jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsRequest");
		jmsConnectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		return jmsConnectionFactory;
	}

	/**
	 * 创建ActiveMQ连接工厂
	 *
	 * @throws JMSException
	 */
	private synchronized ConnectionFactory createActiveConnectionFactory(MQConfig mqConfig) {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(mqConfig.getUser(), mqConfig.getPassword(), "tcp://" + mqConfig.getHost() + ":" + mqConfig.getPort());
		//预先获取消息策略，默认为1000。每次消费取1个，方便留给其他消费者
		ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
		prefetchPolicy.setQueuePrefetch(1);
		activeMQConnectionFactory.setPrefetchPolicy(prefetchPolicy);
		// 默认重复投递6次将转发到死信队列,改为无限次数
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		redeliveryPolicy.setMaximumRedeliveries(-1);
		activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
		activeMQConnectionFactory.setOptimizeAcknowledge(true); //可优化的ACK，延迟确认
		activeMQConnectionFactory.setOptimizeAcknowledgeTimeOut(10000);//ACK超时时间10秒
		activeMQConnectionFactory.setUseAsyncSend(true);//异步发送，默认为true
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
		pooledConnectionFactory.setMaxConnections(mqConfig.getMaxConnections());//最大连接数

		return pooledConnectionFactory;
	}
}
