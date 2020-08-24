package org.git.modules.clm.rcm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.git.common.cache.DictCache;
import org.git.modules.clm.rcm.entity.RcmWarnInfo;

import java.util.List;

/**
 * Chain-Boot
 * 限额检查结果
 *
 * @author caohaijie
 * @version 1.0
 * @description
 * @date 2019/12/20
 * @since 1.8
 */
@Data
@ApiModel(value = "CheckResultVO对象", description = "限额检查结果")
public class CheckResultVO {

//	@ApiModelProperty(value = "是否通过")
//	private boolean isPass;

	@ApiModelProperty(value = "管控类型（0-通过 1-提示 2-触发报备 3-禁止操作）")
	private String controlType;

	@ApiModelProperty(value = "告警信息")
	private String warnMessage;

	@ApiModelProperty(value = "告警详细信息")
	private List<RcmWarnInfo> warnInfos;

	public String getWarnMessage() {
		StringBuilder sb = new StringBuilder();

		if (warnInfos.size() > 0) {
			for (RcmWarnInfo warnInfo : warnInfos) {
				sb.append("在管控节点[" + DictCache.getValue("CD000259", warnInfo.getTriggerControlNode()) + "]，");
				if (warnInfo.getQuotaControlAmt() != null) {
					sb.append("限额指标[" + warnInfo.getQuotaIndexNum() + "]的余额[" + warnInfo.getTriggerAmt() + "元]已超过设定阈值[" + warnInfo.getQuotaControlAmt() + "元]，");

				} else if (warnInfo.getQuotaControlRatio() != null) {
					sb.append("限额指标[" + warnInfo.getQuotaIndexNum() + "]的占比[" + warnInfo.getTriggerAmt() + "%]已超过设定阈值[" + warnInfo.getQuotaControlRatio() + "%]，");
				}
				//应对措施(1-提示；2-触发报备；3-禁止操作)
				sb.append("应对措施[" + DictCache.getValue("CD000260", warnInfo.getNodeMeasure()) + "]；");
			}
		} else {
			return "没有告警信息";
		}
		return sb.toString();
	}

}

