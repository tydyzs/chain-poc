package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

/**
 * ESB基本报文头信息
 */
@Data
@XStreamAlias("Service_Header")
public class ServiceHeader {


	/**
	 * ESB服务码
	 */
	@NotBlank
	@Length(min = 4, max = 14)
	@XStreamAlias("service_id")
	private String serviceId;//1.ESB服务码

	@NotBlank
	@Length(min = 4, max = 4)
	@XStreamAlias("requester_id")
	private String requesterId;//2.请求方系统代码

	@NotBlank
	@Length(min = 2, max = 2)
	@XStreamAlias("channel_id")
	private String channelId;//3.服务渠道代码

	@NotBlank
	@Length(min = 10, max = 26)
	@Pattern(regexp = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)([\\s\\p{Zs}]+)([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).([0-9]{3})$", message = "需要匹配格式yyyy-MM-dd HH:mm:ss.SSS")
	@XStreamAlias("service_time")
	private String serviceTime;//4.请求时间 yyyy-MM-dd HH:mm:ss.SSS

	@NotBlank
	@Length(min = 2, max = 2)
	@Range(min = 01, max = 99)
	@XStreamAlias("version_id")
	private String versionId;//5.版本号

	@NotBlank
	@Length(min = 2, max = 2)
	@XStreamAlias("route_flag")
	private String routeFlag;//6.条件路由标识

	@XStreamAlias("common")
	private String common;//7.预留字段


	//扩展报文头
	@XStreamAlias("EXTERNAL_RULE")
	private String externalRule;//8.外部的规则

	@XStreamAlias("msglog")
	private String msglog;//9.日志等级

	@XStreamAlias("timeout")
	private String timeout;//10.超时时间

	@XStreamAlias("name")
	private String name;//11.服务名称

	@XStreamAlias("service_sn")
	private String serviceSn;//12.ESB流水号

	@XStreamAlias("start_timestamp")
	private String startTimestamp;//13.请求经过MB的开始时间

	@XStreamAlias("service_response")
	private ServiceResponse serviceResponse; // 15判断ESB通讯是否成功

	//作为请求方时，响应的内容字段
	@XStreamAlias("end_timestamp")
	private String endTimestamp;//14.响应经过MB的结束时间

	//作为服务方时，请求和响应的内容字段
	@XStreamAlias("msg_interval")
	private String msgInterval;//消息间隔

	@XStreamAlias("msg_expiry")
	private String msgExpiry;//消息过时

	@XStreamAlias("processes")
	private List<Process> processes;

	@XStreamAlias("message_id")
	private String messageId;//

	@XStreamAlias("correlation_id")
	private String correlationId;//

	@XStreamAlias("response_target")
	private String responseTarget;//

}
