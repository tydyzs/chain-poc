package org.git.modules.clm.front.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.git.common.mq.ConnectionHelper;
import org.git.common.mq.MQConfig;
import org.git.common.mq.MQHelper;
import org.git.common.utils.ValidateUtil;
import org.git.common.utils.XStreamHelper;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description: 江西农信MQ服务类
 * @author: Haijie
 * @date: 2019/12/11
 **/
@Slf4j
@Service
@ConfigurationProperties(prefix = "spring.ibmmq")
public class IBMMQService {

	@Getter
	@Setter
	private MQConfig requester;

	@Getter
	@Setter
	private MQConfig[] provider;


	private MQHelper requesterMQHelper = new MQHelper();
	private List<MQHelper> providerMQHelperList = new ArrayList<>();
	private Map<MQConfig, DefaultMessageListenerContainer> listenerContainerMap = new HashMap<>();
	private XStreamHelper xStreamHelper = new XStreamHelper();

	@Autowired
	private IServiceRecordService serviceRecordService;    //接口记录表

	@PostConstruct
	public void init() {
		log.info("开始初始化MQ配置文件");
		requesterMQHelper.setConfig(requester);

		for (MQConfig mqConfig : provider) {
			MQHelper mqHelper = new MQHelper();
			mqHelper.setHandler(new ServiceHandler());
			mqHelper.setConfig(mqConfig);
			providerMQHelperList.add(mqHelper);
		}
	}

	/**
	 * 停止队列监听
	 */
	public void stopListener() {
		Assert.state(!listenerContainerMap.isEmpty(), "监听没有启动！");
		for (DefaultMessageListenerContainer container : listenerContainerMap.values()) {
			container.stop();
		}
	}


	/**
	 * 启动队列监听
	 *
	 * @throws JMSException
	 */
	public void startListener() throws JMSException {

		//循环创建监听容器
		for (MQHelper mqHelper : providerMQHelperList) {
			//如果已经创建过，则直接启动，否则创建后再启动
			if (listenerContainerMap.keySet().contains(mqHelper.getConfig())) {
				DefaultMessageListenerContainer container = listenerContainerMap.get(mqHelper.getConfig());
				if (container != null && (!container.isActive() || !container.isRunning())) {
					container.start();
				}
				log.warn(mqHelper + "该监听已存在，直接启动！");
			} else {
				DefaultMessageListenerContainer container = mqHelper.createMessageListener(mqHelper.getConfig().getReceiveQueue());
				listenerContainerMap.put(mqHelper.getConfig(), container);
				container.start();
				log.warn(mqHelper + "该监听已经创建，并启动成功！");
			}

		}
	}

	/**
	 * 发送并接收消息
	 *
	 * @param sendQueueName    发送队列名
	 * @param receiveQueueName 接收队列名
	 * @param content          请求内容
	 * @return 响应对象
	 */
	public String request(String sendQueueName, String receiveQueueName, String content) throws JMSException {
		return requesterMQHelper.request(sendQueueName, receiveQueueName, content, MessageType.BYTES);
	}

	/**
	 * 发送并接收消息
	 * @param serviceId 服务代码
	 * @param request   请求对象
	 * @return 响应对象
	 */
	public Response request(String serviceId, Request request) {
		/*获取作为服务请求方的 发送、接收队列*/
		String sendAddress = requester.getSendQueue();//发送队列
		String receiveAddress = requester.getReceiveQueue();//接收队列
		return request(sendAddress, receiveAddress, JxrcbConstant.CLM_TERMINAL_ID, serviceId, request);
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
	public Response request(String sendQueueName, String receiveQueueName, String terminalId, String serviceId, Request request) {
		log.info("---------------------请求端-开始发送MQ消息---------------------");
		log.info("sendQueueName = " + sendQueueName + ", receiveQueueName = " + receiveQueueName + ", terminalId = " + terminalId + ", serviceId = " + serviceId);
		//封装消息对象
		RootNode service = JxrcbUtil.newServiceRQ(terminalId, serviceId);
		service.getServiceBody().setRequest(request);
		//验证报文内容
		ValidateUtil.validate(service);

		//转换为XML格式
		String requestXmlStr = xStreamHelper.marshalXML(service, RootNode.class);
		requestXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + requestXmlStr;//添加XML声明
		log.info("请求端-发送请求报文：\n" + requestXmlStr);
		//存储请求信息
		ServiceRecord serviceRecord = JxrcbUtil.newServiceRecord(service, requestXmlStr);
		serviceRecordService.save(serviceRecord);

		Response response = new Response();//要响应的对象
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			//发送消息
			String responseXmlStr = requesterMQHelper.request(sendQueueName, receiveQueueName, requestXmlStr, MessageType.BYTES);
			log.info("请求端-收到响应报文：\n" + responseXmlStr);
			if (StringUtil.isNotBlank(responseXmlStr)) {

				//将XML转为对象
				RootNode responseObject = null;
				try {
					responseObject = xStreamHelper.unmarshalXML(responseXmlStr, RootNode.class);
				} catch (Exception e) {
					throw new ServiceException("解析响应报文异常：" + e.getMessage());
				}
				//赋值
				serviceRecord.setResponseMessage(responseXmlStr);
				serviceRecord.setStatus(2);//完成
				//返回Response对象
				response = responseObject.getServiceBody().getResponse();
			} else {
				//赋值
				serviceRecord.setResponseMessage("响应为空");
				serviceRecord.setStatus(2);//完成
			}

		} catch (Exception e) {
			e.printStackTrace();
			String error = "MQ请求处理异常！" + e.getMessage();
			log.error(error);
			serviceRecord.setStatus(3);//失败
			serviceRecord.setRequestMessage(error);

			serviceResponse.setStatus(JxrcbConstant.ESB_STATUS_FAIL);
			serviceResponse.setCode("E01");
			serviceResponse.setDesc(error);
			response.setServiceResponse(serviceResponse);

		} finally {
			//存储响应信息
			serviceRecord.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
			serviceRecordService.saveOrUpdate(serviceRecord);
			log.info("---------------------请求端-结束发送MQ消息---------------------");
			return response;
		}
	}


	/**
	 * 接收消息
	 */
	public String receive() throws JMSException {
		//循环创建监听容器
		for (MQHelper mqHelper : providerMQHelperList) {
			String ret = mqHelper.receiveString(mqHelper.getConfig().getReceiveQueue());
			if (StringUtil.isNotBlank(ret)) {
				return ret;
			}
		}
		return "";

	}
}
