package org.git.modules.clm.front.dto.jxrcb.bank;

import com.baomidou.mybatisplus.annotation.TableField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * CLM003_同业实时-明细额度详细信息查询(响应报文response)
 * crd_detail_info(二级额度-成员行)
 */
@Data
@XStreamAlias("crd_main_info")
public class CrdMainInfoResponseDTO {
	@XStreamAlias("crd_grant_org_num")
	private String crdGrantOrgNum;//授信机构


	@Length(max = 24)
	@XStreamAlias("limit_credit")
	private String limitCredit;//授信额度

	@Length(max = 24)
	@XStreamAlias("credit_status")
	private String creditStatus;//授信额度


	@Length(max = 24)
	@XStreamAlias("limit_used")
	private String limitUsed;//已用额度


	@Length(max = 24)
	@XStreamAlias("limit_avi")
	private String limitAvi;//可用额度


	@Length(max = 24)
	@XStreamAlias("exp_credit")
	private String expCredit;//授信敞口


	@Length(max = 24)
	@XStreamAlias("exp_used")
	private String expUsed;//已用敞口


	@Length(max = 24)
	@XStreamAlias("exp_avi")
	private String expAvi;//可用敞口


	@Length(max = 24)
	@XStreamAlias("exp_pre")
	private String expPre;//预占用敞口


	@Length(max = 24)
	@XStreamAlias("limit_frozen")
	private String limitFrozen;//冻结额度


	@Length(max = 24)
	@XStreamAlias("exp_frozen")
	private String expFrozen;//冻结敞口

	@Length(max = 10)
	@XStreamAlias("begin_date")
	private String beginDate;//额度生效日

	@Length(max = 10)
	@XStreamAlias("end_date")
	private String endDate;//额度到期日

	/**
	 * 冻结日期
	 */
	@Length(max = 10)
	@XStreamAlias("FROZEN_DATE")
	private String frozenDate;

	/**
	 * 终止日期
	 */
	@Length(max = 10)
	@XStreamAlias("OVER_DATE")
	private String overDate;

	@Length(max = 24)
	@XStreamAlias("limit_earmark")
	private String limitEarmark;//圈存额度

	@Length(max = 24)
	@XStreamAlias("limit_earmark_used")
	private String limitEarmarkUsed;//圈存已用额度

	/**
	 * 圈存开始日
	 */
	@Length(max = 10)
	@XStreamAlias("EARMARK_BEGIN_DATE")
	private String earmarkBeginDate;

	/**
	 * 圈存到期日
	 */
	@Length(max = 10)
	@XStreamAlias("EARMARK_END_DATE")
	private String earmarkEndDate;
}
