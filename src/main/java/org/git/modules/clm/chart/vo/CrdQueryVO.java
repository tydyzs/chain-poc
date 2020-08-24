package org.git.modules.clm.chart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.credit.entity.CrdSum;
import org.git.modules.clm.credit.entity.TbCrdSum;
import org.git.modules.clm.loan.entity.CrdDetail;

import java.math.BigDecimal;

/**
 * 额度台账、视图，查询客户额度视图参数
 *
 * @author chenchuan
 */
@Data
@ApiModel(value = "CrdQueryVO对象", description = "额度查询列表查询对象")
public class CrdQueryVO extends TbCrdSum {
	@ApiModelProperty(value = "机构名")
	private String orgNumName;

	@ApiModelProperty(value = "币种(CD000019)")
	private String currencyCdName;

	@ApiModelProperty(value = "客户名称")
	private String customerName;

	@ApiModelProperty(value = "客户编号")
	private String customerNum;

	@ApiModelProperty(value = "证件类型")
	private String certType;

	@ApiModelProperty(value = "证件类型(CD000003)")
	private String certTypeName;

	@ApiModelProperty(value = "证件号码")
	private String certNum;

	@ApiModelProperty(value = "机构号")
	private String orgNum;

	@ApiModelProperty(value = "客户经理")
	private String userNum;

	@ApiModelProperty(value = "三级额度品种")
	private String crdDetailPrd;

	@ApiModelProperty(value = "二级额度品种")
	private String crdMainPrd;


	@ApiModelProperty(value = "三级额度品种名称")
	private String crdDetailPrdName;

	@ApiModelProperty(value = "二级额度品种名称")
	private String crdMainPrdName;

	@ApiModelProperty(value = "客户名称")
	private String custName;

	@ApiModelProperty(value = "机构信用代码")
	private String creditOrganCode;

	@ApiModelProperty(value = "机构号备用")
	private String orgNumAnother;

	@ApiModelProperty(value = "机构类型")
	private String orgType;

	@ApiModelProperty(value = "额度产品名称")
	private String crdProductName;

	@ApiModelProperty(value = "额度产品编号")
	private String crdProductNum;

	@ApiModelProperty(value = "机构级别")
	private String deptLevel;

	@ApiModelProperty(value = "客户类型")
	private String customerType;

	@ApiModelProperty(value = "授信机构号")
	private String crdGrantOrgNum;

	/**
	 * 圈存额度
	 */
	@ApiModelProperty(value = "圈存额度")
	private BigDecimal limitEarmark;

	/**
	 * 圈存已用额度
	 */
	@ApiModelProperty(value = "圈存已用额度")
	private BigDecimal limitEarmarkUsed;

	/**
	 * 圈存可用额度
	 */
	@ApiModelProperty(value = "圈存可用额度")
	private BigDecimal limitEarmarkAvi;
}
