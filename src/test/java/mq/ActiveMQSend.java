package mq;

import com.ibm.msg.client.wmq.WMQConstants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.git.core.tool.utils.FileUtil;

import javax.jms.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ActiveMQSend {
	private static String RECEIVE_QUEUE_NAME = "IBM.SERVICE.TYSX.PROVIDER.OUT";
	private static String SEND_QUEUE_NAME = "IBM.SERVICE.TYSX.PROVIDER.IN";

	private static String MQ_URL = "tcp://localhost:61616";//tcp://localhost:1414


	public static void main(String[] args) {
		String fileName101 = "src\\test\\java\\xml\\00870000259101.xml";
		String fileName102 = "src\\test\\java\\xml\\00870000259102.xml";
		String fileName103 = "src\\test\\java\\xml\\00870000259103.xml";
		String fileName109 = "src\\test\\java\\xml\\00870000259109.xml";

		String xmlStr = FileUtil.readToString(new File(fileName101));
		System.out.println("将要发送的消息内容：" + xmlStr);
		Session session = null;

		try {
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
			Connection connection = activeMQConnectionFactory.createConnection();
			session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue(SEND_QUEUE_NAME);
			MessageProducer producer = session.createProducer(queue);
			BytesMessage bytesMessage = session.createBytesMessage();
			bytesMessage.writeBytes(xmlStr.getBytes());
			producer.send(bytesMessage);
			session.commit();
			session.close();
			connection.close();
			System.out.println("发送完成!");
		} catch (JMSException e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (JMSException ex) {
				ex.printStackTrace();
			}

		}


	}

}
