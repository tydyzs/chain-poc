package org.git.modules.clm.front.service.impl;

import org.git.common.mq.MQHelper;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.modules.clm.front.service.IServiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.concurrent.CountDownLatch;

@Service
public class RemoteMessageListener implements MessageListener {

	private CountDownLatch latch;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private IServiceRecordService serviceRecordService;

	public RemoteMessageListener(CountDownLatch latch) {
		this.latch = latch;
	}

	public RemoteMessageListener() {
	}


	@Override
	public void onMessage(Message m) {
		ServiceRecordServiceImpl serviceRecordService = SpringUtil.getBean(ServiceRecordServiceImpl.class);
//		Object obj = applicationContext.getBean("serviceRecordServiceImpl");
		System.out.println(serviceRecordService);
//		latch.countDown();
		System.out.println("------------ [RemoteMessageListener] begin receive the message !------------");

		//将解析数据存入数据库
		ServiceRecord serviceRecord = new ServiceRecord();
		serviceRecord.setRequestorCode("3232");
		serviceRecord.setServiceCode("3232");
		serviceRecord.setResponderCode("UCS10001");//我方系统代码
		serviceRecord.setRemark("测试数据");
		serviceRecord.setRequestMessage("请求报文.....");
		serviceRecord.setResponseMessage("响应报文.....");
		serviceRecordService.saveOrUpdate(serviceRecord);

		try {

			String replyMessage = "";//回复消息
			String receiveMessage = "";//收到的消息
			//消息校验
			if (m instanceof BytesMessage) {//接收字节对象
				BytesMessage message = (BytesMessage) m;
				receiveMessage = MQHelper.messageToString(message);
			} else {
				replyMessage = "JMS消息类型只支持BytesMessage！";
			}

			System.out.println("监听到的内容：" + receiveMessage);
			if (Func.isBlank(receiveMessage)) {
				replyMessage = "JMS消息内容为空！";
			} else if (receiveMessage.length() < 100) {
				replyMessage = "JMS消息内容非法！";
			} else {
				//将XML转为对象
				replyMessage = "消息转换处理成功";
			}

//			Destination reply = m.getJMSReplyTo();
//			MessageProducer producer = session.createProducer(reply);
//			BytesMessage bytesMessage = session.createBytesMessage();
//			bytesMessage.writeBytes(replyMessage.getBytes());
//			producer.send(bytesMessage);

			m.acknowledge();


			System.out.println(replyMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("------------ [RemoteMessageListener] end receive the message !------------");

	}
}
