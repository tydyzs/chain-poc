package org.git.modules.clm.chart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.credit.entity.TbCrdSum;

/**
 * 额度台账、视图，查询客户额度视图参数
 *
 * @author chenchuan
 */
@Data
@ApiModel(value = "CrdStatisVO对象", description = "额度统计对象")
public class CrdStatisVO {

	@ApiModelProperty(value = "额度品种编号")
	private String crdDetailPrd;

	@ApiModelProperty(value = "企业规模")
	private String unitScale;

	@ApiModelProperty(value = "客户类型")
	private String customerType;

	@ApiModelProperty(value = "担保方式")
	private String guaranteeType;

	@ApiModelProperty(value = "行业")
	private String industry;

	@ApiModelProperty(value = "业务品种")
	private String productNum;

	@ApiModelProperty(value = "维度中文名称")
	private String keyName;

	@ApiModelProperty(value = "批复敞口金额（本期）")
	private String approveExpAmount;

	@ApiModelProperty(value = "批复敞口金额（同期）")
	private String approveExpAmountTq;

	@ApiModelProperty(value = "批复敞口金额（同期增幅）")
	private String approveExpAmountTqup;

	@ApiModelProperty(value = "批复敞口金额（年初）")
	private String approveExpAmountNc;

	@ApiModelProperty(value = "批复敞口金额（年初增幅）")
	private String approveExpAmountNcup;

	@ApiModelProperty(value = "批复敞口金额（上期）")
	private String approveExpAmountSq;

	@ApiModelProperty(value = "批复敞口金额（上期增幅）")
	private String approveExpAmountSqup;

	@ApiModelProperty(value = "授信敞口余额（本期）")
	private String creditExpBalance;

	@ApiModelProperty(value = "授信敞口余额（同期）")
	private String creditExpBalanceTq;

	@ApiModelProperty(value = "授信敞口余额（同期增幅）")
	private String creditExpBalanceTqup;

	@ApiModelProperty(value = "授信敞口余额（年初）")
	private String creditExpBalanceNc;

	@ApiModelProperty(value = "授信敞口余额（年初增幅）")
	private String creditExpBalanceNcup;

	@ApiModelProperty(value = "授信敞口余额（年初）")
	private String creditExpBalanceSq;

	@ApiModelProperty(value = "授信敞口余额（年初增幅）")
	private String creditExpBalanceSqup;

	@ApiModelProperty(value = "贷款敞口余额（本期）")
	private String loanExpBalance;

	@ApiModelProperty(value = "贷款敞口余额（同期）")
	private String loanExpBalanceTq;

	@ApiModelProperty(value = "贷款敞口余额（同期增幅）")
	private String loanExpBalanceTqup;

	@ApiModelProperty(value = "贷款敞口余额（年初）")
	private String loanExpBalanceNc;

	@ApiModelProperty(value = "贷款敞口余额（年初增幅）")
	private String loanExpBalanceNcup;

	@ApiModelProperty(value = "贷款敞口余额（上期）")
	private String loanExpBalanceSq;

	@ApiModelProperty(value = "贷款敞口余额（上期增幅）")
	private String loanExpBalanceSqup;


	@ApiModelProperty(value = "授信额度（本期）")
	private String limitCredit;

	@ApiModelProperty(value = "授信额度（同期）")
	private String limitCreditTq;

	@ApiModelProperty(value = "授信额度（同期增幅）")
	private String limitCreditTqup;

	@ApiModelProperty(value = "授信额度（年初）")
	private String limitCreditNc;

	@ApiModelProperty(value = "授信额度（年初增幅）")
	private String limitCreditNcup;

	@ApiModelProperty(value = "授信额度（上期）")
	private String limitCreditSq;

	@ApiModelProperty(value = "授信额度（上期增幅）")
	private String limitCreditSqup;
}
