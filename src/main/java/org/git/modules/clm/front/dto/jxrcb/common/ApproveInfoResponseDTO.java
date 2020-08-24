package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * CLM002_通用-额度查询(响应报文response)
 * approve_info(额度批复信息)
 */
@Data
@XStreamAlias("approve_info")
public class ApproveInfoResponseDTO {

	@Length(max = 50)
	@XStreamAlias("approve_num")
	private String approveNum;//批复编号

	@Length(max = 10)
	@XStreamAlias("currency_cd")
	private String currencyCd;//币种 CD000019
	// 01人民币元  13港元  27日元  39英镑  14美元  38欧元  59其他


	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("approve_amt")
	private String approveAmt;//批复金额 单笔业务时为申请借款金额

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("approve_used")
	private String approveUsed;//批复已用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("approve_avi")
	private String approveAvi;//批复可用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("approve_exp_amt")
	private String approveExpAmt;//批复敞口金额

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("approve_exp_used")
	private String approveExpUsed;//批复敞口已用

	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("approve_exp_avi")
	private String approveExpAvi;//批复敞口可用

	@Length(max = 50)
	@XStreamAlias("product_num")
	private String productNum;//业务品种

	@Length(max = 50)
	@XStreamAlias("product_type")
	private String productType;//业务种类 CD000061
	/*10 自营贷款                    9909 其他垫款
	1001 个人经营性贷款             	2003 信贷资产转移
	1002 个人消费性贷款             	3004 商票保贴
	1003 流动资金贷款               	10 自营贷款
	1004 固定资产贷款               	1003 流动资金贷款
	20 委托贷款                     	1004 固定资产贷款
	2001 公积金委托                 	20 委托贷款
	2002 一般委托贷款               	2001 公积金委托
	30 贴现资产                     	2002 一般委托贷款
	3002 转贴现                     	40 贸易融资
	3003 再贴现                     	4001 国内贸易融资
	40 贸易融资                     	50 承兑汇票
	4001 国内贸易融资               	5001 承兑汇票
	50 承兑汇票                     	60 保函
	5001 承兑汇票                   	6001 保函
	60 保函                         70 信用证
	6001 非融资性国内保函           	7001 信用证
	70 信用证                       	4002 国际贸易融资
	7001 信用证                     	99 垫款
	4002 国际贸易融资               	9901 银行承兑汇票垫款
	99 垫款                         9902 贴现贷款
	9901 银行承兑汇票垫款           	9903 保函业务垫款
	9902 贴现贷款                   	9904 信用证垫款
	9903 保函业务垫款               	9909 其他垫款
	9904 信用证垫款                 	2003 信贷资产转移
                                  	3004 商票保贴*/

	@Length(max = 2)
	@XStreamAlias("is_circle")
	private String isCycle;//额度循环标志 CD000167   1是 0否

	@Length(max = 10)
	@XStreamAlias("industry")
	private String industry;//行业投向 CD000015

	@Length(max = 10)
	@XStreamAlias("guarantee_type")
	private String guaranteeType;//担保方式 CD000100
	//0 信用  1 保证  2 抵押  3 质押


	@Length(max = 10)
	@XStreamAlias("main_guarantee_type")
	private String mainGuaranteeType;//主担保方式 CD000100
	//0 信用  1 保证  2 抵押  3 质押


	@Pattern(regexp = "^(\\d{0,18}\\.\\d{0,2})$",message = "类型应为NUMBER(18,2)")
	@Length(max = 24)
	@XStreamAlias("term")
	private String term;//申请期限

	@Length(max = 10)
	@XStreamAlias("term_unit")
	private String termUnit;//申请期限单位 CD000169
	//Y 年  M 月  D 日

	@Length(max = 50)
	@XStreamAlias("project_num")
	private String projectNum;//项目协议号
}
