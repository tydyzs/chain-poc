
package mq;

import com.ibm.mq.jms.MQDestination;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.git.core.tool.utils.DigestUtil;
import org.git.core.tool.utils.FileUtil;

import javax.jms.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * 模拟请求方系统，发送请求并等待接收响应
 * @author caohaijie
 */
public class IBMRequest {

	private static final Logger logger = Logger.getLogger(IBMRequest.class.getTypeName());

	//MQ服务器配置
	private static final String HOST = "10.0.123.80";
	private static final int PORT = 40003;
	private static final String CHANNEL = "SVRCONN_GW_IN"; // 通道名称
	private static final String QMGR = "MQ_Transaction_Server"; // 队列管理器

	//要模拟请求的系统
	private static final String REQUEST_ID = "0007";

	//要请求的报文内容
	private static final String requestFile = "00870000259102.xml";

	//请求系统的队列配置信息
	private static final Map<String, String[]> queueConfig = new HashMap();

	static {
		//信贷系统
		queueConfig.put("0007", new String[]{"IBM.SERVICE.LOAN.REQUESTER.IN", "IBM.SERVICE.LOAN.REQUESTER.OUT"});
		//资金业务
		queueConfig.put("0010", new String[]{"IBM.SERVICE.ZJYYMGR.REQUESTER.IN", "IBM.SERVICE.ZJYYMGR.REQUESTER.OUT"});
		//票据系统
		queueConfig.put("0039", new String[]{"IBM.SERVICE.PRS.REQUESTER.IN", "IBM.SERVICE.LOAN.REQUESTER.OUT"});
		//ECIF
		queueConfig.put("0088", new String[]{"IBM.SERVICE.ECIF.REQUESTER.IN", "IBM.SERVICE.LOAN.REQUESTER.OUT"});
	}


	public static void main(String[] args) throws Exception {

		String requestXml = FileUtil.readToString(new File("src\\test\\java\\xml\\" + requestFile));

		logger.info("发送消息开始...");

		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory connectionFactory = ff.createConnectionFactory();
		connectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
		connectionFactory.setIntProperty(WMQConstants.WMQ_PORT, PORT);
		connectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
		connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		connectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
		connectionFactory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsRequest");
		connectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
//		connectionFactory.setStringProperty(WMQConstants.USERID, APP_USER);
//		connectionFactory.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
//		if (CIPHER_SUITE != null && !CIPHER_SUITE.isEmpty()) {
//			connectionFactory.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, CIPHER_SUITE);
//		}
		logger.info("服务器已连接！");

		JMSContext context = connectionFactory.createContext();
		Destination destination = context.createQueue(queueConfig.get(REQUEST_ID)[0]);//请求队列
		((MQDestination) destination).setTargetClient(WMQConstants.WMQ_CLIENT_NONJMS_MQ);
		JMSProducer producer = context.createProducer();
		logger.info("要发送的内容：" + requestXml);
		TextMessage message = context.createTextMessage(requestXml);

		String correlationID = String.format("%24.24s", UUID.randomUUID().toString());
		message.setJMSExpiration(900000);
//		logger.info("correlationID十六进制：" + ActiveMQService.getHexString(correlationID.getBytes()));
		message.setJMSCorrelationID(correlationID);

		//correlationID发送后，MQ服务器会将其转为（ID: + 十六进制），因此接收时要按照十六进制接收。
		//MessageID默认发送后，MQ服务器不是按照十六进制，具体编码方式未知，因此在发送成功后获取自动生成的MessageID进行接收。

//		TemporaryQueue requestQueue = context.createTemporaryQueue();//临时队列

		Queue replyQueue = context.createQueue(queueConfig.get(REQUEST_ID)[1]);//响应队列
//		message.setJMSReplyTo(replyQueue);//指定响应队列
		producer.send(destination, message);
		String selector = "JMSCorrelationID='ID:" + DigestUtil.encodeHex(message.getJMSCorrelationID().getBytes()) + "'";
		logger.info("发送请求完成！");
		logger.info("发送后MessageID ：" + message.getJMSMessageID());
		logger.info("发送后CorrelationID ：" + message.getJMSCorrelationID());
		logger.info("消息选择器：" + "selector = " + selector);
		JMSConsumer consumer = context.createConsumer(replyQueue, selector);
		logger.info("等待接收响应....");
		Message receivedMessage = consumer.receive();
		String response = convertString(receivedMessage);
		logger.info("收到的响应内容为: \n" + response);

	}


	public static String convertString(Message message) {

		try {
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
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

}
