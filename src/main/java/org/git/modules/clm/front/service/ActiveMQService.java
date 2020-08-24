package org.git.modules.clm.front.service;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.git.common.utils.ValidateUtil;
import org.git.common.utils.XStreamHelper;
import org.git.core.tool.utils.DateUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.modules.clm.front.service.impl.RemoteMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.jms.Queue;
import javax.jms.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@EnableConfigurationProperties(ActiveMQConfig.class)
@Deprecated
public class ActiveMQService {

	//当前MQ连接
	private ConnectionFactory connectionFactory;

	//监听器容器
	private DefaultMessageListenerContainer container;

	//已启动监听的消息队列
	private List<String> alreadyQueueName = new ArrayList();

	//activeMQ配置
	@Autowired
	private ActiveMQConfig activeMQConfig;

	//接口记录表
	@Autowired
	private IServiceRecordService serviceRecordService;

	private XStreamHelper xStreamHelper = new XStreamHelper();

//	CountDownLatch latch = new CountDownLatch(100);//线程执行计数器

	private String connectUrl;

	public String getUrl() {
		return connectUrl;
	}

	public ActiveMQService() {

	}

	@PostConstruct
	public void init() {
		getConnectionFactory();
	}


	/**
	 * 创建ActiveMQ连接工厂
	 *
	 * @throws JMSException
	 */
	private synchronized ConnectionFactory createActiveConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = null;
		try {
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(activeMQConfig.getUser(), activeMQConfig.getPassword(), activeMQConfig.getBrokerUrl());
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
//			activeMQConnectionFactory.setUseAsyncSend(true);//异步发送，默认为true
			pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
			pooledConnectionFactory.setMaxConnections(activeMQConfig.getMaxConnections());//最大连接数
//			pooledConnectionFactory.setIdleTimeout(activeMQConfig.getIdleTimeout());//空闲连接超时时间,默认30000毫秒
//			pooledConnectionFactory.setMaximumActiveSessionPerConnection(100);//每个连接最大的会话数量，默认500
//			pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);//会话池满了是否阻塞，默认ture
//			pooledConnectionFactory.setExpiryTimeout(expiryTimeout);//默认0
//			pooledConnectionFactory.setUseAnonymousProducers(true);//匿名生产作者，默认true
//			pooledConnectionFactory.setCreateConnectionOnStartup(true);//是否在启动连接池时创建连接，默认true
//			pooledConnectionFactory.setReconnectOnException(true);
//			pooledConnectionFactory.initConnectionsPool();
//			pooledConnectionFactory.start();
		} catch (Exception jmsex) {
			recordFailure(jmsex);
			return null;
		}
		return pooledConnectionFactory;
	}


	public Session getQueueSession() throws JMSException {
		/**
		 * createSession方法里有两个参数，
		 * 第一个参数表示是否使用事务（false不能使用 commit方法）
		 * 第二个参数表示消息的确认模式。消息的确认模式共有4种：
		 * 0：SESSION_TRANSACTED 事务提交并确认
		 * 1：AUTO_ACKNOWLEDGE 自动确认
		 * 2：CLIENT_ACKNOWLEDGE 客户端手动确认
		 * 3：DUPS_OK_ACKNOWLEDGE 自动批量确认
		 * 4：INDIVIDUAL_ACKNOWLEDGE 单条消息确认 为AcitveMQ自定义的ACK_MODE
		 */
		//从连接池获取一个连接
		Connection connection = getConnectionFactory().createConnection();
		//启动连接
		connection.start();
		//创建点对点消息队列，启动事务并提交确认
		Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

		return session;
	}


	/**
	 * 停止并发接收MQ监听器
	 */
	public void stop() {
		if (isStarted()) {
			container.stop();
		}
	}

	/**
	 * 判断容器是否启动
	 *
	 * @return
	 */
	public boolean isStarted() {
		if (container != null && container.isActive() && container.isRunning()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断容器的队列是否启动
	 *
	 * @param queueName
	 * @return
	 */
	public boolean isStarted(String queueName) {
		if (isStarted() && alreadyQueueName.contains(queueName)) {
			return true;
		}
		return false;
	}


	/**
	 * 启动并发接收MQ监听容器
	 *
	 * @param queueName 要监听的队列名
	 */
	public void startListenerContainer(String queueName, String replyQueueName) {
		if (isStarted(queueName)) {
			return;//已经启动则直接返回
		}
		//为空或没有监听该队列，则新建
		if (container == null || !alreadyQueueName.contains(queueName)) {

			container = new DefaultMessageListenerContainer();

			//①配置连接池
			getConnectionFactory();
			container.setConnectionFactory(connectionFactory);
			container.setCacheLevel(3);
//			container.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
			ServiceErrorHandler serviceErrorHandler = new ServiceErrorHandler();
			container.setErrorHandler(serviceErrorHandler);
//			container.setExceptionListener();
			//②配置消息监听适配器
			MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();

			//设置代理的监听器（实现MessageListener）或处理类(不能实现MessageListener)
			RemoteMessageListener remoteMessageListener = new RemoteMessageListener();
			ServiceHandler frontHandler = new ServiceHandler();
//			messageListenerAdapter.setDelegate(remoteMessageListener);//执行的类
			messageListenerAdapter.setDelegate(frontHandler);//执行的类
			messageListenerAdapter.setDefaultListenerMethod(MessageListenerAdapter.ORIGINAL_DEFAULT_LISTENER_METHOD);//执行的方法
			//设置消息转换器
			XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
			xStreamMarshaller.setStreamDriver(XStreamHelper.XPP3_DRIVER);
			xStreamMarshaller.setAnnotatedClasses(RootNode.class);//设置从类级注释元数据中读取别名的带注释的类。
			xStreamMarshaller.setAutodetectAnnotations(true);
			//设置OX解析映射
//			Map map = new HashMap<String, RootNode>();
//			map.put("ServiceHeader", org.git.modules.clm.front.dto.jxrcb.ServiceHeader.class);
//			map.put("ServiceBody", org.git.modules.clm.front.dto.jxrcb.ServiceBody.class);
//			map.put("Service", org.git.modules.clm.front.dto.jxrcb.RootNode.class);
//			xStreamMarshaller.setAliases(map);//设置别名
//			ClassLoaderReference classLoaderReference = new ClassLoaderReference(ServiceHeader.class.getClassLoader());
//			DefaultMapper defaultMapper = new DefaultMapper(classLoaderReference);
//			xStreamMarshaller.setMapper(defaultMapper);
//			MapperWrapper mapperWrapper1 = new SystemAttributeAliasingMapper(defaultMapper);
//			ClassAliasingMapper mapperWrapper2 = new ClassAliasingMapper(defaultMapper);
//			mapperWrapper2.addClassAlias("ServiceHeader",ServiceHeader.class);
//			xStreamMarshaller.setMapperWrappers(mapperWrapper2.getClass());

			//转换器1:直接转为对象
			MarshallingMessageConverter messageConverter = new MarshallingMessageConverter();
			messageConverter.setUnmarshaller(xStreamMarshaller);//解析接收的消息 BytesMessage -> Service
			messageConverter.setMarshaller(xStreamMarshaller);//封装发送的消息 Result -> BytesMessage
			messageConverter.setTargetType(MessageType.BYTES);
			//转换器2:按message类型和byte[]、String进行互转。 BytesMessage <-> byte[]  TextMessage <-> String
			SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();

			//设置转换器
			messageListenerAdapter.setMessageConverter(simpleMessageConverter);
			//如果接收的消息没有回复通道，则使用默认回复通道
			messageListenerAdapter.setDefaultResponseQueueName(replyQueueName);
			container.setMessageListener(messageListenerAdapter);
			//③设置并发的消费者
			container.setConcurrentConsumers(activeMQConfig.getConcurrentConsumers());
			//④设置发送目标
			container.setDestinationName(queueName);

			//⑤初始化方法必须执行
			container.initialize();

			// 添加到已监听列表
			alreadyQueueName.add(queueName);
		}

		//启动容器
		container.start();
	}

	/**
	 * 判断服务器连接
	 *
	 * @return
	 */
	public boolean testConnected() {

		try {
			connectionFactory.createConnection();
		} catch (JMSException e) {
			return false;
		}
		return true;
	}

	/**
	 * 单次接收消息
	 *
	 * @param receiveQueueName 接收的队列名称
	 * @throws JMSException
	 */
	public String receiveMessageAsString(String receiveQueueName, String selector) {
		try {
			Session queueSession = getQueueSession();
			Queue queue = queueSession.createQueue(receiveQueueName);
			MessageConsumer queueReceiver = queueSession.createConsumer(queue, selector);
			log.info("请求端开始等待，响应超时30秒....");
			Date begin = Calendar.getInstance().getTime();
			Message message = queueReceiver.receive(30000);//30秒超时
			Date end = Calendar.getInstance().getTime();
			long abs = DateUtil.between(begin, end).getSeconds();
			log.info("请求端收到响应，实际等待" + abs + "秒");
			String responseXmlStr = convertString(message);
			return responseXmlStr;
		} catch (JMSException e) {
			e.printStackTrace();
			log.error("MQ消息接收失败！");
		}
		return null;
	}

	/**
	 * 单次接收消息
	 *
	 * @param receiveQueueName 接收的队列名称
	 * @throws JMSException
	 */
	public Response receiveMessageAsObject(String receiveQueueName, String selector) {
		String responseXmlStr = receiveMessageAsString(receiveQueueName, selector);
		//将XML转为对象
		RootNode responseObject = xStreamHelper.unmarshalXML(responseXmlStr, RootNode.class);
		return responseObject.getServiceBody().getResponse();
	}

	/**
	 * 发送并接收消息
	 *
	 * @param sendQueueName    发送队列名
	 * @param receiveQueueName 接收队列名
	 * @param terminalId       终端代码
	 * @param serviceId        服务代码
	 * @param request          请求对象
	 * @return 响应对象
	 */
	public Response sendAndReceiveMessage(String sendQueueName, String receiveQueueName, String terminalId, String serviceId, Request request) {
		log.info("---------------------请求端-开始发送MQ消息---------------------");
		log.info("sendQueueName = " + sendQueueName + ", receiveQueueName = " + receiveQueueName + ", terminalId = " + terminalId + ", serviceId = " + serviceId);
		//封装消息对象
		RootNode service = JxrcbUtil.newServiceRQ(terminalId, serviceId);
		service.getServiceBody().setRequest(request);
		//验证报文内容
		ValidateUtil.validate(service);

		//转换为XML格式
		String requestXmlStr = xStreamHelper.marshalXML(service, RootNode.class);
		log.info("请求端-发送请求报文：\n" + requestXmlStr);
		//存储请求信息
		ServiceRecord serviceRecord = JxrcbUtil.newServiceRecord(service, requestXmlStr);
		serviceRecordService.save(serviceRecord);
		String responseXmlStr = null;
		try {
			//发送消息

			String selector = sendMessage(sendQueueName, receiveQueueName, requestXmlStr);
			if (StringUtil.isNotBlank(selector)) {//如果发送成功，则开始接收新消息
				responseXmlStr = receiveMessageAsString(receiveQueueName, selector);
				log.info("请求端-收到响应报文：\n" + responseXmlStr);
				if (StringUtil.isNotBlank(responseXmlStr)) {

					//将XML转为对象
					RootNode responseObject = xStreamHelper.unmarshalXML(responseXmlStr, RootNode.class);

					//赋值
					serviceRecord.setResponseMessage(responseXmlStr);
					serviceRecord.setStatus(2);//完成

					//返回Response对象
					return responseObject.getServiceBody().getResponse();
				} else {
					//赋值
					serviceRecord.setResponseMessage("响应为空");
					serviceRecord.setStatus(2);//完成
				}

			}
		} catch (Exception e) {
			recordFailure(e);
			String error = "发送或响应异常，可能XML格式有误！";
			log.error(error);
			serviceRecord.setStatus(3);//失败
			serviceRecord.setRequestMessage(error);

		} finally {
			//存储响应信息
			serviceRecord.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
			serviceRecordService.saveOrUpdate(serviceRecord);
			log.info("---------------------请求端-结束发送MQ消息---------------------");
		}
		return null;
	}


	/**
	 * 发送并接收消息
	 *
	 * @param sendQueueName    发送队列名
	 * @param receiveQueueName 接收队列名
	 * @param message          消息内容
	 * @return 响应对象
	 */
	public Response sendAndReceiveMessage(String sendQueueName, String receiveQueueName, String message) {

		//发送消息
		String selector = sendMessage(sendQueueName, receiveQueueName, message);
		if (StringUtil.isNotBlank(selector)) {//如果发送成功，则开始接收新消息
			return receiveMessageAsObject(receiveQueueName, selector);
		}
		return null;
	}


	/**
	 * 单次发送（字节类型）消息
	 *
	 * @param sendQueueName 发送队列名称
	 * @param message
	 * @return selector 生成的selector
	 * @throws JMSException
	 */
	public String sendMessage(String sendQueueName, String receiveQueueName, String message) {
		if (sendQueueName == null) {
			log.error("消息通道地址不能为空！");
			return null;
		}

		try {
			Session queueSession = getQueueSession();
			Queue queue = queueSession.createQueue(sendQueueName);
			MessageProducer messageProducer = queueSession.createProducer(queue);
			BytesMessage msg = queueSession.createBytesMessage();
			msg.writeBytes(message.getBytes());
			//设置消息ID
			String correlationID = String.format("%24.24s", UUID.randomUUID().toString());
//			String selector = "JMSCorrelationID='ID:" + DigestUtil.encodeHex(correlationID.getBytes()) + "'";
			String selector = "JMSCorrelationID='" + correlationID + "'";
			msg.setJMSCorrelationID(correlationID);
			log.info("设置消息选择器：" + selector);
			msg.setJMSExpiration(900000);//消息过期时间15分钟
			log.debug("设置消息过期时间：15分钟");
			//设置响应地址
			if (StringUtil.isNotBlank(receiveQueueName)) {
				Destination reply = queueSession.createQueue(receiveQueueName);
				msg.setJMSReplyTo(reply);
			}
			//执行发送
			messageProducer.send(msg);
			// 在事务会话中，只有commit之后，消息才会真正到达目的地
			queueSession.commit();
			//发送完消息后就关闭，不保留消息生产者对象
			messageProducer.close();
			return selector;
		} catch (JMSException e) {
			e.printStackTrace();
			log.error("MQ消息发送失败！");
		}
		return null;

	}

	/**
	 * 将消息转为字符串
	 *
	 * @param message
	 * @return
	 * @throws JMSException
	 */
	public static String convertString(Message message) throws JMSException {

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

	public ConnectionFactory getConnectionFactory() {
		if (connectionFactory == null) {
			if (activeMQConfig != null && activeMQConfig.isEnable()) {
				connectUrl = activeMQConfig.getBrokerUrl();
				connectionFactory = createActiveConnectionFactory();
			} else {
				log.warn("MQ服务配置没有启用！");
			}
		}
		Assert.notNull(connectionFactory, "创建连接工厂失败");
		return connectionFactory;
	}


	private static void recordFailure(Exception ex) {
		if (ex != null) {
			if (ex instanceof JMSException) {
				processJMSException((JMSException) ex);
			} else {
				log.warn(ex.getMessage());
			}
		}
		log.info("FAILURE");
		return;
	}

	private static void processJMSException(JMSException jmsex) {
		log.warn(jmsex.getMessage());
		Throwable innerException = jmsex.getLinkedException();
		if (innerException != null) {
			log.warn("Inner exception(s):");
		}
		while (innerException != null) {
			log.warn(innerException.getMessage());
			innerException = innerException.getCause();
		}
		return;
	}

	public static void main(String[] args) throws JMSException {
		ActiveMQService service = new ActiveMQService();
//		service.startMessageListener("activemq.queue.out");
		service.startListenerContainer("", "");
	}
}
