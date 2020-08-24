package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM101_信贷系统-额度申请(响应报文response)
 * CLM102_信贷系统-合同信息同步
 * CLM103_信贷系统-借据信息同步
 * quota_info(预警信息数组)
 */
@Data
@XStreamAlias("quota_info")
public class QuotaInfoResponseDTO {

	@XStreamAlias("quota_index_num")
	private String quotaIndexNum = "";//限额指标编号 限额已用金额-预警时点数

	@XStreamAlias("quota_total_sum")
	private BigDecimal quotaTotalSum = BigDecimal.ZERO;//限额总额

	@XStreamAlias("quota_index_caliber")
	private String quotaIndexCaliber;//统计口径 1-贷款敞口余额；2-授信余额；3-批复敞口金额；4、同业业务

	@XStreamAlias("quota_used_amt")
	private BigDecimal quotaUsedAmt = BigDecimal.ZERO;//限额已用金额 限额可用金额-预警时点数

	@XStreamAlias("quota_free_amt")
	private BigDecimal quotaFreeAmt = BigDecimal.ZERO;//限额可用金额 限额已用比率-预警时点数

	@XStreamAlias("quota_used_ratio")
	private BigDecimal quotaUsedRatio = BigDecimal.ZERO;//限额已用比率 限额可用比率-预警时点数

	@XStreamAlias("quota_free_ratio")
	private BigDecimal quotaFreeRatio = BigDecimal.ZERO;//限额可用比率

	@XStreamAlias("use_org_num")
	private String useOrgNum = "";//限额生效机构

	@XStreamAlias("trigger_level")
	private String triggerLevel;//阈值层级

	@XStreamAlias("quota_control_amt")
	private BigDecimal quotaControlAmt = BigDecimal.ZERO;//预警阀值(余额)

	@XStreamAlias("quota_control_ratio")
	private BigDecimal quotaControlRatio = BigDecimal.ZERO;//预警阀值(比例)

	@XStreamAlias("trigger_amt")
	private BigDecimal triggerAmt = BigDecimal.ZERO;//触发当前值(余额)

	@XStreamAlias("trigger_ratio")
	private BigDecimal triggerRatio = BigDecimal.ZERO;//触发当前值(占比)

	@XStreamAlias("trigger_control_node")
	private String triggerControlNode;//触发管控节点 1-提示；2-触发报备；3-禁止操作

	@XStreamAlias("node_measure")
	private String nodeMeasure = "";//应对措施
}
