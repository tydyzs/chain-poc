package org.git.modules.clm.front.dto.jxrcb.bank;

import com.baomidou.mybatisplus.annotation.TableField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * CLM003_同业实时-明细额度详细信息查询(响应报文response)
 * crd_detail_info(额度明细数据)
 */
@Data
@XStreamAlias("crd_detail_info")
public class CrdDetailInfoResponseDTO {

	@XStreamAlias("crd_grant_org_num")
	private String crdGrantOrgNum;//授信机构

	
	@Length(max = 24)
	@XStreamAlias("limit_credit")
	private String limitCredit;//授信额度

	
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
	@XStreamAlias("limit_pre")
	private String limitPre;//预占用额度

	
	@Length(max = 24)
	@XStreamAlias("exp_pre")
	private String expPre;//预占用敞口

	
	@Length(max = 24)
	@XStreamAlias("limit_earmark")
	private String limitEarmark;//圈存额度

	@Length(max = 10)
	@XStreamAlias("earmark_begin_date")
	private String earmarkBeginDate;//圈存开始日

	@Length(max = 10)
	@XStreamAlias("earmark_end_date")
	private String earmarkEndDate;//圈存到期日

	@Length(max = 24)
	@XStreamAlias("limit_earmark_used")
	private String limitEarmarkUsed;//圈存已用额度

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

	@Length(max = 10)
	@XStreamAlias("credit_status")
	private String creditStatus;//额度状态

	@Length(max = 10)
	@XStreamAlias("crd_admit_flag")
	private String crdAdmitFlag;//准入状态

	@Length(max = 10)
	@XStreamAlias("is_circle")
	private String isCircle;//是否可循环

	@Length(max = 10)
	@XStreamAlias("is_mix")
	private String isMix;//是否可串用

	@Length(max = 30)
	@XStreamAlias("mix_credit")
	private String mixCredit;//可串用额度

	@Length(max = 30)
	@XStreamAlias("mix_used")
	private String mixUsed;//串用已用

	@Length(max = 10)
	@XStreamAlias("tran_user_no")
	private String tranUserNo;//客户经理

}
