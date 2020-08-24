package org.git.modules.clm.chart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.credit.entity.TbCrdSubcontract;

/**
 * 对外担保信息
 *
 * @author chenchuan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SubcontractVO对象", description = "对外担保信息")
public class GrtVO extends TbCrdSubcontract {

	@ApiModelProperty(value = "被担保客户名称")
	private String customerName;

	@ApiModelProperty(value = "抵质押人/保证人客户编号")
	private String suretyCustomerNum;

	@ApiModelProperty(value = "合同编号")
	private String contractNum;

	@ApiModelProperty(value = "本次担保金额")
	private String suretyAmt;

	@ApiModelProperty(value = "担保物类型")
	private String pledgeType;

	@ApiModelProperty(value = "担保物编号")
	private String suretyNum;

	@ApiModelProperty(value = "担保物类型 CD000209")
	private String pledgeTypeName;

	@ApiModelProperty(value = "担保合同类型 CD000102")
	private String subcontractTypeName;

	@ApiModelProperty(value = "经办机构 SysCache")
	private String orgNumName;

	@ApiModelProperty(value = "币种 CD000019")
	private String currencyCdName;

	@ApiModelProperty(value = "经办人 UserCache")
	private String userNumName;

}
