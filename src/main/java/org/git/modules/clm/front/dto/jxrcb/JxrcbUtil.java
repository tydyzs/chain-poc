package org.git.modules.clm.front.dto.jxrcb;

import org.apache.commons.lang3.StringUtils;
import org.git.common.utils.BizNumUtil;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.front.entity.ServiceRecord;
import org.git.core.launch.utils.INetUtil;
import org.git.core.tool.utils.DateTimeUtil;
import org.git.core.tool.utils.DateUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JxrcbUtil {


	/**
	 * 创建报文请求结构
	 *
	 * @param serviceId 对方系统代码
	 * @param serviceId 对方服务代码
	 * @return
	 */
	public static RootNode newServiceRQ(String terminalId, String serviceId) {
		RootNode rootNode = new RootNode();
		ServiceHeader serviceHeader = newServiceHeaderRQ(serviceId, terminalId);

		ServiceBody serviceBody = new ServiceBody();
		ExtAttributes extAttributes = newExtAttributesRQ(serviceId);
		serviceBody.setExtAttributes(extAttributes);
		rootNode.setServiceBody(serviceBody);

		rootNode.setServiceHeader(serviceHeader);
		rootNode.setServiceBody(serviceBody);

		return rootNode;
	}


	/**
	 * 创建ServiceHeader ESB请求头
	 *
	 * @param serviceId
	 * @return
	 */
	public static ServiceHeader newServiceHeaderRQ(String serviceId, String terminalId) {
		ServiceHeader serviceHeader = new ServiceHeader();
		serviceHeader.setServiceId(serviceId);//ESB服务码
		serviceHeader.setRequesterId(JxrcbConstant.CLM_TERMINAL_ID);//请求方系统代码
		serviceHeader.setChannelId(terminalId != null & terminalId.length() >= 2 ? terminalId.substring(0, 2) : "00");//提供方系统定义的渠道代码相同,前2位
		serviceHeader.setServiceTime(DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATETIME_ALL));//请求时间
		serviceHeader.setVersionId("01");//版本号
		serviceHeader.setRouteFlag("99");//条件路由标识
		serviceHeader.setCommon("");//预留字段
		serviceHeader.setServiceSn("");//ESB流水号
		return serviceHeader;
	}

	/**
	 * 创建ExtAttributes业务请求头
	 *
	 * @return ExtAttributes
	 */
	private static ExtAttributes newExtAttributesRQ(String serviceId) {
		ExtAttributes extAttributes = new ExtAttributes();
		extAttributes.setReqServId(serviceId.substring(serviceId.length() - 6));//服务提供方系统自定义交易编码,必填
		extAttributes.setIterateNo("0");//数组条数
		extAttributes.setRequesterId(JxrcbConstant.CLM_TERMINAL_ID);//请求方系统代码
		extAttributes.setChannelId(JxrcbConstant.CLM_TERMINAL_ID);//渠道代码
		extAttributes.setOriReqId(JxrcbConstant.CLM_TERMINAL_ID);//源请求方系统代码
		extAttributes.setOriChaId(JxrcbConstant.CLM_TERMINAL_ID);//源请求方渠道代码
		extAttributes.setReqServcode("comm01");//系统交易识别码
		extAttributes.setChannelServcode("comm01");//渠道交易识别码
		extAttributes.setOriReqServcode("");//源系统交易识别码
		extAttributes.setOriChaServcode("");//源渠道交易识别码
		extAttributes.setTerminalId("pts/108");//交易发起端终端号
		extAttributes.setBranchId("99999");//请求方机构代码 必填
		extAttributes.setOperatorId("99999");//请求方操作员代码 必填
		extAttributes.setSubAuthTeller("");//网点授权柜员
		extAttributes.setAuthPsw01("");//授权密码1
		extAttributes.setBranAuthTeller("");//成员行社授权柜员
		extAttributes.setAuthPsw02("");//授权密码2
		extAttributes.setCenAuthTeller01("");//中心授权柜员1
		extAttributes.setAuthPsw03("");//授权密码3
		extAttributes.setCenAuthTeller02("");//中心授权柜员2

		String date = DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATE);
		extAttributes.setOriReqDate(date);//业务发起端日期2015-01-09 必填
		String reqSn = BizNumUtil.getBizSn();
		extAttributes.setOriReqSn(reqSn);//业务发起端流水 必填
		extAttributes.setOriReqTimestamp(DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATETIME_ALL));//业务发起时间戳2015-03-04 10:51:39.113 必填
		extAttributes.setRequesterDate(date);//前置日期 必填
		extAttributes.setRequesterSn(reqSn);//前置流水 必填
		extAttributes.setMacValue(INetUtil.getHostIp());//Mac值
//		extAttributes.setExecGrp("");//执行组名称
//		extAttributes.setBroker("");//代理名称
		return extAttributes;

	}


	public static ServiceRecord newServiceRecord(RootNode requestRootNode, String requestMessage) {
		ServiceRecord serviceRecord = new ServiceRecord();
		if (requestRootNode != null) {
			serviceRecord.setRequestorCode(requestRootNode.getServiceHeader().getRequesterId()); //请求方系统代码
			serviceRecord.setServiceCode(requestRootNode.getServiceHeader().getServiceId());//ESB服务码（交易接口编号）
			serviceRecord.setServiceSn(requestRootNode.getServiceHeader().getServiceSn());//交易流水号
			serviceRecord.setBizSn(requestRootNode.getServiceBody().getExtAttributes().getRequesterSn());//请求业务流水编号
			serviceRecord.setServiceTime(Timestamp.valueOf(requestRootNode.getServiceHeader().getServiceTime()));//交易时间

		} else if (StringUtils.isNotBlank(requestMessage)) {
			serviceRecord.setRequestorCode(StringUtils.substringBetween(requestMessage, "<requester_id>", "</requester_id>"));
			serviceRecord.setServiceCode(StringUtils.substringBetween(requestMessage, "<service_id>", "</service_id>"));//ESB服务码（交易接口编号）
			serviceRecord.setServiceSn(StringUtils.substringBetween(requestMessage, "<service_sn>", "</service_sn>"));//交易流水号
			serviceRecord.setBizSn(StringUtils.substringBetween(requestMessage, "<requester_sn>", "</requester_sn>"));//请求业务流水编号
		}
		serviceRecord.setResponderCode(JxrcbConstant.CLM_TERMINAL_ID);//我方系统代码
		serviceRecord.setRemark("");
		serviceRecord.setStatus(1);//待处理
		serviceRecord.setRequestMessage(requestMessage);//请求原始报文
		serviceRecord.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));//实际创建时间

		return serviceRecord;
	}

}
