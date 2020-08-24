package org.git.modules.clm.rcm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Chain-Boot
 * 限额检查所需的业务数据
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2019/12/20
 * @since 1.8
 */
@Data
public class CheckBizInfoVO {

	@ApiModelProperty(value = "关联业务流水号")
	@NotBlank
	private String tranSeqSn;

	@ApiModelProperty(value = "关联业务编号")
	@NotBlank
	private String bizNum;

	/**
	 * 业务类型(1、额度申请  2、合同申请 3、放款申请4、同业业务申请)
	 */
	@ApiModelProperty(value = "业务类型(1、额度申请  2、合同申请 3、放款申请4、同业业务申请)")
	@NotBlank
	private String bizType;

	/**
	 * 0、所有阶段；
	 * 1、授信申请流程中；
	 * 2、授信申请通过；
	 * 3、合同申请流程中；
	 * 4、合同申请生效；
	 * 5、放款申请流程中；
	 * 6、放款申请生效
	 */
	@ApiModelProperty(value = "0、所有阶段；1、授信申请流程中；2、授信申请通过；3、合同申请流程中；4、合同申请生效、5放款申请流程中、6放款申请生效")
	@NotBlank
	private String controlNode;

	@ApiModelProperty(value = "业务客户经理")
	@NotBlank
	private String userNum;

	@ApiModelProperty(value = "业务网点机构")
	@NotBlank
	private String orgNum;

	@ApiModelProperty(value = "客户编号")
	@NotBlank
	private String customerNum;

	@ApiModelProperty(value = "客户名称")
	@NotBlank
	private String customerName;

	@ApiModelProperty(value = "业务品种")
	@NotBlank
	private String productNum;

	@ApiModelProperty(value = "发生金额")
	@NotBlank
	private BigDecimal amt;

	@ApiModelProperty(value = "币种")
	@NotBlank
	private String currencyCd;

	//------------------------------------
	@ApiModelProperty(value = "客户类型")
	@NotBlank
	private String customerType;

	@ApiModelProperty(value = "国别")
	@NotBlank
	private String rangeCountry;

	//以下为tb_rcm_index_credit非同业独有
	@ApiModelProperty(value = "区域")
	private String rangeRegion;

	@ApiModelProperty(value = "行业")
	private String rangeIndustry;

	@ApiModelProperty(value = "期限范围")
	@TableField("range_term")
	private String rangeTerm;

	@ApiModelProperty(value = "风险缓释")
	private String rangerRiskMitigation;

	//以下为tb_rcm_index_bank同业独有
	@ApiModelProperty(value = "业务场景")
	private String bussScene;

	@ApiModelProperty(value = "业务类型")
	private String businessType;
}
