/*
 * (c) Copyright IBM Corporation 2019
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mq;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.*;
import java.util.logging.Logger;

/**
 * 模拟响应方系统，接收请求并发送响应
 *
 * @author caohaijie
 */
public class IBMResponse {

	private static final Logger logger = Logger.getLogger(IBMResponse.class.getTypeName());

//	private static String HOST = "10.0.114.119";//1
	private static String HOST = "10.0.114.181";//2
	private static int PORT = 40003;
	private static String CHANNEL = "SVRCONN_GW_IN";
	private static String QMGR = "MQ_Transaction_Server";
	private static String PROVIDER_IN_QUEUE_NAME = "IBM.SERVICE.TYSX.PROVIDER.IN";//监听请求队列
	private static String PROVIDER_OUT_QUEUE_NAME = "IBM.SERVICE.TYSX.PROVIDER.OUT";//响应队列


	static String responseXml = "";//响应内容，默认返回请求内容

	public static void main(String[] args) throws Exception {
		logger.info("接收消息开始...");

		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory connectionFactory = ff.createConnectionFactory();
		connectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
		connectionFactory.setIntProperty(WMQConstants.WMQ_PORT, PORT);
		connectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
		connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		connectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
		connectionFactory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsResponse");
		connectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		logger.info("服务器已连接！");

		JMSContext context = connectionFactory.createContext();
		Destination destination = context.createQueue(PROVIDER_IN_QUEUE_NAME);
		JMSConsumer consumer = context.createConsumer(destination);

		logger.info("开始接收。。。");
		Message receivedMessage = consumer.receive();
		logger.info("接收后MessageID：" + receivedMessage.getJMSMessageID());
		logger.info("接收后CorrelationID：" + receivedMessage.getJMSCorrelationID());
		String requestXml = convertString(receivedMessage);
		logger.info("接收到的消息：" + requestXml);
		if ("".equals(responseXml)) {
			responseXml = requestXml;//默认把请求信息返回
		}
		logger.info("接收后响应消息：\n" + responseXml);

		if (receivedMessage instanceof Message) {
			BytesMessage message = context.createBytesMessage();
			message.writeBytes(responseXml.getBytes());

			message.setJMSMessageID(receivedMessage.getJMSMessageID());
			JMSProducer producer = context.createProducer();
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//			Destination replyTo = receivedMessage.getJMSReplyTo();//使用对方指定的响应队列
			Destination replyTo = context.createQueue(PROVIDER_OUT_QUEUE_NAME);
			message.setJMSCorrelationID(receivedMessage.getJMSCorrelationID());
			producer.send(replyTo, message);
			logger.info("响应消息完成!");
			logger.info("响应的MessageID:" + message.getJMSMessageID());
		}
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
