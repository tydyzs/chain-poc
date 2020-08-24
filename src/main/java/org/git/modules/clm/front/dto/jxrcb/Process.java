package org.git.modules.clm.front.dto.jxrcb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 以下是processes子节点(
 * <processes nextprocess="1" total="1" currentprocess="1">
 * 		<process id="1">
 * 	注：响应报文标红属性也要返回
 * )
 */
@Data
@XStreamAlias("process")
public class Process {
	@XStreamAlias("end_timestamp")
	private String endTimestamp;//

	@XStreamAlias("process_timestamp")
	private String processTimestamp;//

	@XStreamAlias("target_q")
	private String targetQ;//

	@XStreamAlias("sub_target_id")
	private String subTargetId;//

	@XStreamAlias("target_id")
	private String targetId;//

	@XStreamAlias("sub_service_sn")
	private String subServiceSn;//

	@XStreamAlias("async_reversal_service_id")
	private String asyncReversalServiceId;//

	@XStreamAlias("is_end")
	private String isEnd;//

	@XStreamAlias("skip_to_process")
	private String skipToProcess;//

	@XStreamAlias("after_logic_class")
	private String afterLogicClass;//

	@XStreamAlias("timeout")
	private String timeout;//

	@XStreamAlias("reversal_seq")
	private String reversalSeq;//

	@XStreamAlias("key_service")
	private String keyService;//

	@XStreamAlias("status")
	private String status;//

	@XStreamAlias("service_id")
	private String serviceId;//


}
