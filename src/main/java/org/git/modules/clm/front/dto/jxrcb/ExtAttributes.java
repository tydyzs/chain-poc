package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 业务应用头信息规范（ext_attributes）
 */
@Data
@XStreamAlias("ext_attributes")
public class ExtAttributes {

	@Length(min = 6, max = 6)
	@XStreamAlias("req_serv_id")
	private String reqServId;//1.交易代码

	@XStreamAlias("iterate_no")
	private String iterateNo;//2.数组条数

	@XStreamAlias("requester_id")
	private String requesterId;//3.求方系统代码

	@XStreamAlias("channel_id")
	private String channelId;//4.渠道代码

	@XStreamAlias("ori_req_id")
	private String oriReqId;//5.源请求方系统代码

	@XStreamAlias("ori_cha_id")
	private String oriChaId;//6.源请求方渠道代码

	@XStreamAlias("req_servcode")
	private String reqServcode;//7.系统交易识别码

	@XStreamAlias("channel_servcode")
	private String channelServcode;//8.渠道交易识别码

	@XStreamAlias("ori_req_servcode")
	private String oriReqServcode;//9.源系统交易识别码

	@XStreamAlias("ori_cha_servcode")
	private String oriChaServcode;//10.源渠道交易识别码

	@XStreamAlias("terminal_id")
	private String terminalId;//11.终端号

	@XStreamAlias("branch_id")
	private String branchId;//12.请求方机构代码

	@XStreamAlias("operator_id")
	private String operatorId;//13.请求方操作员代码

	@XStreamAlias("sub_auth_teller")
	private String subAuthTeller;//14.网点授权柜员

	@XStreamAlias("auth_psw01")
	private String authPsw01;//15.授权密码1

	@XStreamAlias("bran_auth_teller")
	private String branAuthTeller;//16.成员行社授权柜员

	@XStreamAlias("auth_psw02")
	private String authPsw02;//17.授权密码2

	@XStreamAlias("cen_auth_teller01")
	private String cenAuthTeller01;//18.中心授权柜员1

	@XStreamAlias("auth_psw03")
	private String authPsw03;//19.授权密码3

	@XStreamAlias("cen_auth_teller02")
	private String cenAuthTeller02;//20.中心授权柜员2

	@XStreamAlias("ori_req_date")
	private String oriReqDate;//21.业务发起端日期

	@XStreamAlias("ori_req_sn")
	private String oriReqSn;//22.业务发起端流水

	@XStreamAlias("ori_req_timestamp")
	private String oriReqTimestamp;//23.业务发起时间戳

	@XStreamAlias("requester_date")
	private String requesterDate;//24.前置日期

	@XStreamAlias("requester_sn")
	private String requesterSn;//25.前置流水

	@XStreamAlias("mac_value")
	private String macValue;//26.Mac值

//	@XStreamAlias("response_status")
//	private String responseStatus;//响应状态
//
//	@XStreamAlias("response_code")
//	private String responseCode;//响应代码
//
//	@XStreamAlias("response_desc")
//	private String responseDesc;//响应描述


	//扩展业务头
	@XStreamAlias("ExecGrp")
	private String ExecGrp;//执行组名称

	@XStreamAlias("Broker")
	private String Broker;//代理名称

	//业务响应头
	@Length(max = 10)
	@XStreamAlias("status")
	private String status;//交易状态

	@Length(max = 50)
	@XStreamAlias("code")
	private String code;//返回码

	@Length(max = 256)
	@XStreamAlias("desc")
	private String desc;//返回信息

	@Length(max = 10)
	@XStreamAlias("trans_date")
	private String transDate;//交易日期 2024-12-01

	@Length(max = 26)
	@XStreamAlias("trans_time")
	private String transTime;//交易时间 15:06:23

	@Length(max = 30)
	@XStreamAlias("serial_num")
	private String serialNum;//流水号


}
