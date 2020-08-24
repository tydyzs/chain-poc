package org.git.common.mq;

import lombok.Getter;
import lombok.Setter;
import org.git.core.tool.utils.DigestUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.front.service.ServiceErrorHandler;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import javax.jms.*;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * MQ助手类
 *
 * @author caohaijie
 * @date 2019/12/12
 * @version 1.0
 * @since 1.8
 */
public class MQHelper {

	public static final String HANDLER_METHOD = MessageListenerAdapter.ORIGINAL_DEFAULT_LISTENER_METHOD;

	private ConnectionFactory connectionFactory;
	private Object handler;
	private ConnectionHelper connectionHelper = new ConnectionHelper();

	@Getter
	@Setter
	private MQConfig config;

	public MQHelper() {

	}


	/**
	 * 设置回调处理类
	 * 实现MessageListener或普通类带handleMessage方法
	 *
	 * @param handler 处理类
	 */
	public void setHandler(Object handler) {
		this.handler = handler;
	}

	/**
	 * 获取回调处理类
	 *
	 * @return 处理类
	 */
	public Object getHandler() {
		Assert.state(handler != null, "No handler set");
		return handler;
	}


	/**
	 * 获取MQ连接工厂
	 */
	@Nullable
	public ConnectionFactory getConnectionFactory() {
		if (connectionFactory == null) {
			try {
				connectionFactory = connectionHelper.createConnectionFactory(config);
			} catch (JMSException e) {
				e.printStackTrace();
				Assert.state(false, "创建MQ连接工厂失败");
			}
		}
		Assert.state(connectionFactory != null, "No connectionFactory set");
		return connectionFactory;
	}


	/**
	 * 自动监听模式（同步）
	 *
	 * @param queueName 监听队列
	 */
	public DefaultMessageListenerContainer createMessageListener(String queueName) throws JMSException {
		//创建消息监听容器
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		//①配置连接池
		container.setConnectionFactory(getConnectionFactory());//连接工厂
		container.setCacheLevel(3);//缓存级别
		//0：SESSION_TRANSACTED 事务提交并确认
		//1：AUTO_ACKNOWLEDGE 自动确认
		//2：CLIENT_ACKNOWLEDGE 客户端手动确认
		//3：DUPS_OK_ACKNOWLEDGE 自动批量确认
		container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);//消息的确认模式
		container.setSessionTransacted(true);//是否使用事务（false不能使用 commit方法）
		ServiceErrorHandler serviceErrorHandler = new ServiceErrorHandler();
		container.setErrorHandler(serviceErrorHandler);
		//②配置消息监听适配器
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
		messageListenerAdapter.setDelegate(getHandler());//执行的类
		messageListenerAdapter.setDefaultListenerMethod(HANDLER_METHOD);//执行的方法handleMessage
		//设置消息转换器:按message类型和byte[]、String进行互转。 BytesMessage <-> byte[]  TextMessage <-> String
		SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();
		messageListenerAdapter.setMessageConverter(simpleMessageConverter);
		messageListenerAdapter.setDefaultResponseQueueName(config.getSendQueue());//接收的消息没有回复通道，则使用默认回复通道
		container.setMessageListener(messageListenerAdapter);
		container.setConcurrentConsumers(config.getConcurrentConsumer());//并发的消费者数量
		container.setDestinationName(queueName);//要监听的队列
		container.initialize();//初始化方法必须执行
//		container.start();//启动容器，放在外面启动
		return container;
	}


	/**
	 * 获取CorrelationID
	 *
	 * @return 字符串
	 */
	public String getCorrelationID() {
		return String.format("%24.24s", UUID.randomUUID().toString());
	}


	/**
	 * 单次接收:接收消息后不响应
	 *
	 * @param receiveQueueName 接收队列
	 * @return MQ消息对象
	 */
	public Message receiveMessage(String receiveQueueName) {
		return receiveMessage(receiveQueueName, null);
	}

	/**
	 * 单次接收:接收消息后不响应
	 *
	 * @param receiveQueueName 接收队列
	 * @param messageSelector  消息选择器
	 * @return MQ消息对象
	 */
	public Message receiveMessage(String receiveQueueName, String messageSelector) {
		ConnectionFactory connectionFactory = getConnectionFactory();
		assert connectionFactory != null;
		JMSContext jmsContext = connectionFactory.createContext();
		Queue receiveQueue = jmsContext.createQueue(receiveQueueName);
		JMSConsumer jmsConsumer = jmsContext.createConsumer(receiveQueue, messageSelector);
		Message message = jmsConsumer.receive(config.getWaitTimeout());
		return message;
	}

	/**
	 * 单次接收:接收消息后不响应
	 *
	 * @param receiveQueueName 接收队列
	 * @return 字符串类型消息
	 * @throws JMSException 异常
	 */
	public String receiveString(String receiveQueueName) throws JMSException {
		return receiveString(receiveQueueName, null);
	}

	/**
	 * 单次接收:接收消息后不响应
	 *
	 * @param receiveQueueName 接收队列
	 * @param messageSelector  消息选择器
	 * @return 字符串类型消息
	 * @throws JMSException 异常
	 */
	public String receiveString(String receiveQueueName, String messageSelector) throws JMSException {
		Message message = receiveMessage(receiveQueueName, messageSelector);
		return messageToString(message);
	}

	/**
	 * 单次发送：发送后不等待响应
	 *
	 * @param sendQueueName 发送队列
	 * @param content       内容
	 * @param messageType   内容类型
	 * @throws JMSException 异常
	 */
	public void send(String sendQueueName, String content, MessageType messageType) throws JMSException {
		send(sendQueueName, content, messageType, null);
	}

	/**
	 * 单次发送：发送后不等待响应
	 *
	 * @param sendQueueName 发送队列
	 * @param content       内容
	 * @param messageType   内容类型
	 * @param correlationID 消息ID
	 * @throws JMSException 异常
	 */
	public void send(String sendQueueName, String content, MessageType messageType, String correlationID) throws JMSException {
		ConnectionFactory connectionFactory = getConnectionFactory();
		assert connectionFactory != null;
		JMSContext jmsContext = connectionFactory.createContext();
		Queue sendQueue = jmsContext.createQueue(sendQueueName);
		JMSProducer jmsProducer = jmsContext.createProducer();
		Message message = stringToMessage(content, messageType, jmsContext);
		if (StringUtil.isNotBlank(correlationID)) {
			assert message != null;
			message.setJMSCorrelationID(correlationID);
		}
		jmsProducer.send(sendQueue, message);
//		jmsContext.commit();
	}

	/**
	 * 单次请求：发送并等待响应
	 *
	 * @param sendQueueName    发送队列
	 * @param receiveQueueName 接收队列
	 * @param content          内容
	 * @param messageType      内容类型
	 * @return String响应内容
	 * @throws JMSException 异常
	 */
	public String request(String sendQueueName, String receiveQueueName, String content, MessageType messageType) throws JMSException {
		String correlationID = getCorrelationID();
		send(sendQueueName, content, messageType, correlationID);//发送消息，并设置correlationID
		//发送后，将correlationID转为十六进制，拼接选择器
		String selector = "JMSCorrelationID='ID:" + DigestUtil.encodeHex(correlationID.getBytes()) + "'";
		return receiveString(receiveQueueName, selector);
	}

	/**
	 * 单次响应：接收并发送响应
	 *
	 * @param sendQueueName    发送队列
	 * @param receiveQueueName 接收队列
	 * @param messageType      消息类型
	 * @throws JMSException 异常
	 */
	public void response(String sendQueueName, String receiveQueueName, MessageType messageType) throws JMSException {
		Message requestMessage = receiveMessage(receiveQueueName, null);//收到请求消息，不过滤
		Object responseMessage = null;//处理后的返回消息
		try {
			Method method = getHandler().getClass().getMethod(HANDLER_METHOD, Object.class);//处理逻辑
			responseMessage = ReflectionUtils.invokeMethod(method, getHandler(), requestMessage);
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = "处理请求消息失败！";
		}
		send(sendQueueName, (String) responseMessage, messageType, requestMessage.getJMSCorrelationID());
	}


	/**
	 * 将String转为Message对象
	 *
	 * @param message 字符串
	 * @param type    消息类型
	 * @param session 会话
	 * @return Message对象
	 * @throws JMSException 异常
	 */
	public static Message stringToMessage(String message, MessageType type, JMSContext session) throws JMSException {

		if (type.equals(MessageType.BYTES)) {
			BytesMessage bytesMessage = session.createBytesMessage();
			bytesMessage.writeBytes(message.getBytes());
			return bytesMessage;
		} else if (type.equals(MessageType.TEXT)) {
			TextMessage textMessage = session.createTextMessage(message);
			return textMessage;
		} else if (type.equals(MessageType.OBJECT)) {
			ObjectMessage objectMessage = session.createObjectMessage(message);
			return objectMessage;
		} else if (type.equals(MessageType.MAP)) {
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("key", message);
			return mapMessage;
		}
		return null;

	}


	/**
	 * 将Message对象转为String
	 *
	 * @param message 对象
	 * @return 字符串
	 * @throws JMSException
	 */
	public static String messageToString(Message message) throws JMSException {

		StringBuilder sb = new StringBuilder();
		if (message instanceof BytesMessage) {//字节消息
			BytesMessage msg = (BytesMessage) message;
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = msg.readBytes(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
		} else if (message instanceof TextMessage) {//文本消息
			TextMessage msg = (TextMessage) message;
			String res = msg.getText();
			sb.append(res);
		} else if (message instanceof MapMessage) {//键值消息
			MapMessage msg = (MapMessage) message;
			System.out.println(msg.getString("username"));
		} else if (message instanceof StreamMessage) {//流消息
			StreamMessage msg = (StreamMessage) message;
			System.out.println(msg.readString());
			System.out.println(msg.readLong());
		} else if (message instanceof ObjectMessage) {//对象类型
			ObjectMessage msg = (ObjectMessage) message;
			Object user = msg.getObject();
		}

		return sb.toString();
	}
}
