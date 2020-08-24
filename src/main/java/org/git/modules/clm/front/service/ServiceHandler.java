package org.git.modules.clm.front.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.git.common.utils.BizNumUtil;
import org.git.common.utils.CommonUtil;
import org.git.common.utils.ValidateUtil;
import org.git.common.utils.XStreamHelper;
import org.git.core.log.exception.ServiceException;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.SpringUtil;
import org.git.modules.clm.front.api.jxrcb.JxrcbAPI;
import org.git.modules.clm.front.dto.jxrcb.*;
import org.git.modules.clm.front.entity.ServiceConfig;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.modules.clm.front.service.impl.ServiceConfigServiceImpl;
import org.git.modules.clm.front.service.impl.ServiceRecordServiceImpl;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 前置通用处理类
 *
 * @Author caohaijie
 */
@Slf4j
public class ServiceHandler {


	public Object handleMessage(Object message) {
		log.info("---------------------开始处理MQ请求---------------------");
		IServiceRecordService serviceRecordService = SpringUtil.getBean(ServiceRecordServiceImpl.class);

		String requestMessage = null;//请求报字符串
		RootNode requestRootNode = null;//请求报文根节点
		XStreamHelper xStreamHelper = new XStreamHelper();//XML解析工具
		ServiceRecord serviceRecord;

		//-----解析请求报文------
		try {
			if (message instanceof byte[]) {
				requestMessage = new String((byte[]) message);
			} else if (message instanceof String) {
				requestMessage = String.valueOf(message);
			} else {
				String error = "MQ消息只接受MessageByte类型，实际收到类型为：" + message.getClass().getTypeName();
				throw new ServiceException(error);
			}
			log.info("收到请求消息：\n" + requestMessage);
			Object requestObject = xStreamHelper.unmarshalXML(requestMessage, RootNode.class);
			//处理报文对象
			if (requestObject instanceof RootNode) {
				requestRootNode = (RootNode) requestObject;//响应报文根节点
			} else {
				throw new ServiceException(requestObject.getClass().getTypeName() + "该XML报文对象不支持！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String error = "请检查报文格式！" + e.getCause().getMessage();
			log.error(error);
			if (error.length() > 120) {
				error = error.substring(0, 120);
			}
			error = errorRequestXML(requestMessage, error);//处理错误xml
			return error.getBytes();
		} finally {
			//将解析数据存入数据库
			serviceRecord = JxrcbUtil.newServiceRecord(requestRootNode, requestMessage);
			serviceRecordService.save(serviceRecord);//执行插入数据库
		}

		//-----解析成功后------
		RootNode responseRootNode = new RootNode();//响应报文根节点
		responseRootNode.setServiceHeader(requestRootNode.getServiceHeader());//和请求内容一致---结束ServiceHeader的赋值----
		ServiceBody responseServiceBody = new ServiceBody();//响应报文体
//		responseServiceBody.setRequest(requestRootNode.getServiceBody().getRequest());//和请求内容一致
		ExtAttributes responseExtAttributes = new ExtAttributes();//响应业务报文头
//		BeanUtils.copyProperties(requestRootNode.getServiceBody().getExtAttributes(), responseExtAttributes);//复制请求内容
		ServiceResponse resultAPI = new ServiceResponse();//API处理结果(自定义-只使用对象，但不赋值XML)

		try {

			//校验数据
			ValidateUtil.validate(requestRootNode);//验证长度/必输等

			//验证我方交易处理配置信息，并执行调用
			String serviceId = requestRootNode.getServiceHeader().getServiceId();
			IServiceConfigService serviceConfigService = SpringUtil.getBean(ServiceConfigServiceImpl.class);
			ServiceConfig serviceConfig = serviceConfigService.getOneByCode(serviceId);
			if (serviceConfig == null) {
				throw new ServiceException(serviceId + "服务配置不存在！");
			}
			String invokeApi = serviceConfig.getInvokeApi();
			if (Func.isEmpty(invokeApi)) {
				throw new ServiceException(serviceId + "服务调用API没有配置！");
			}

			//执行接口处理类，返回Response对象
			try {
				ServiceBody requestServiceBody = requestRootNode.getServiceBody();
				JxrcbAPI jxrcbAPI = (JxrcbAPI) Class.forName(invokeApi).newInstance();
				Response response = jxrcbAPI.run(requestServiceBody);//处理后获得响应报文体

				ValidateUtil.validate(response);//验证响应报文体

				responseServiceBody.setResponse(response);//响应报文体
				resultAPI = response.getServiceResponse();//响应结果
			} catch (Exception e) {
				throw new ServiceException(invokeApi + "执行失败！" + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultAPI.setStatus(JxrcbConstant.ESB_STATUS_FAIL);
			resultAPI.setCode("E01");
			resultAPI.setDesc("ServiceHandler处理异常!" + e.getMessage());

		} finally {

			String responseMessage = "";
			try {
				responseExtAttributes.setStatus(resultAPI.getStatus());
				responseExtAttributes.setCode(resultAPI.getCode());
				responseExtAttributes.setDesc(resultAPI.getDesc());
				String workTime = CommonUtil.getWorkDateTime2();
				responseExtAttributes.setTransDate(workTime.substring(0, 10));
				responseExtAttributes.setTransTime(workTime.substring(11));
				responseExtAttributes.setSerialNum(BizNumUtil.getBizSn());
				responseServiceBody.setExtAttributes(responseExtAttributes);//---结束ExtAttributes的赋值----
				responseRootNode.setServiceBody(responseServiceBody);//----结束ServiceBody的赋值----

				//将响应结果转化为XML格式，并存储结果
				responseMessage = xStreamHelper.marshalXML(responseRootNode, RootNode.class);
				responseMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + responseMessage;//添加XML声明
				log.info("响应消息：\n" + responseMessage);

				serviceRecord.setResponseMessage(responseMessage);
				serviceRecord.setStatus(JxrcbConstant.ESB_STATUS_COMPLETE.equalsIgnoreCase(resultAPI.getStatus()) ? 2 : 3);//2 处理成功 3处理失败
				serviceRecord.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
				serviceRecordService.saveOrUpdate(serviceRecord);
			} catch (Exception e) {
				e.printStackTrace();
				responseMessage = "处理异常！";
			}

			log.info("---------------------结束处理MQ请求---------------------");
			return responseMessage.getBytes();

		}

	}

	/**
	 * 请求xml解析失败，添加错误信息后返回
	 *
	 * @param requestXml 请求报文
	 * @param errorMsg   响应错误内容
	 * @return
	 */
	public String errorRequestXML(String requestXml, String errorMsg) {
		String status = "FAIL";
		String code = "-1";
		if (requestXml == null) {
			return "";
		}
		//如果包含以下内容，说明为合法xml格式
		if (requestXml.contains("<Service>") //
			&& requestXml.contains("<Service_Header>") //
			&& requestXml.contains("<Service_Body>")//
			&& requestXml.contains("</ext_attributes>")//
		) {
			//如果含有response_status等，则清理
			int ca = requestXml.indexOf("<response_status>");
			if (ca != -1) {
				String a = requestXml.substring(ca, requestXml.indexOf("</response_status>") + "</response_status>".length());
				requestXml = requestXml.replaceAll(a + "\n*", "");
			}
			int cb = requestXml.indexOf("<response_code>");
			if (cb != -1) {
				String b = requestXml.substring(cb, requestXml.indexOf("</response_code>") + "</response_code>".length());
				requestXml = requestXml.replaceAll(b + "\n*", "");
			}
			int cc = requestXml.indexOf("<response_desc>");
			if (cc != -1) {
				String c = requestXml.substring(cc, requestXml.indexOf("</response_desc>") + "</response_desc>".length());
				requestXml = requestXml.replaceAll(c + "\n*", "");
			}

			//拼接业务头错误信息
			int extIndex = requestXml.indexOf("</ext_attributes>");
			String xmlA = requestXml.substring(0, extIndex);
			String xmlB = requestXml.substring(extIndex);
			String result = xmlA + "\t<response_status>" + status + "</response_status>\n" +
				"\t<response_code>" + code + "</response_code>\n" +
				"\t<response_desc>" + errorMsg + "</response_desc>\n" + xmlB;

			return result;
		}
		return requestXml;
	}


}
