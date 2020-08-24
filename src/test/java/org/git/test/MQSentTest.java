package org.git.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MQSentTest {

	public static void main(String[] args) {
		File directory = new File("");// 参数为空
		String courseFile = null;
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileName3=courseFile+"\\src\\test\\java\\xml\\00870000259207.xml";
		String xml = readFileContent(fileName3);

		Session session = null;

		try {
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection = activeMQConnectionFactory.createConnection();
			session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue("IBM.SERVICE.TYSX.PROVIDER.IN");
			MessageProducer producer = session.createProducer(queue);
			BytesMessage bytesMessage = session.createBytesMessage();

			bytesMessage.writeBytes(xml.getBytes());
			Message message = session.createTextMessage("test,test");
			producer.send(bytesMessage);
			session.commit();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (JMSException ex) {
				ex.printStackTrace();
			}

		}
//		session.close();
//		connection.close();

	}


	public static String readFileContent(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sbf.append(tempStr);
			}
			reader.close();
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sbf.toString();
	}
}
