package org.git.modules.clm.chart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;

/**
 * 集团360视图，查询集团客户额度视图参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrdGroupVO对象", description = "集团客户额度查询对象")
public class CrdGroupVO extends TbCrdStatisCsm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "成员客户编号")
	@TableField("MEMBER_CUSTOMER_NUM")
	private String memberCustomerNum;

	@ApiModelProperty(value = "二级额度编号")
	@TableId("CRD_MAIN_NUM")
	private String crdMainNum;

	@ApiModelProperty(value = "二级额度产品")
	@TableField("CRD_MAIN_PRD")
	private String crdMainPrd;

	@ApiModelProperty(value = "三级额度编号")
	@TableId("CRD_DETAIL_NUM")
	private String crdDetailNum;

	@ApiModelProperty(value = "额度产品编号")
	@TableField("CRD_DETAIL_PRD")
	private String crdDetailPrd;

	@ApiModelProperty(value = "额度产品名称")
	@TableId("CRD_PRODUCT_NAME")
	private String crdProductName;

	@ApiModelProperty(value = "额度产品编号")
	@TableId("CRD_PRODUCT_Num")
	private String crdProductNum;

	@ApiModelProperty(value = "额度父类编号")
	@TableId("SUPER_CRD_NUM")
	private String superCrdNum;

	@ApiModelProperty(value = "额度父类名称")
	@TableId("SUPER_CRD_NAME")
	private String superCrdName;

	@ApiModelProperty(value = "经办机构名称")
	@TableId("ORG_NAME")
	private String orgName;

	@ApiModelProperty(value = "经办机构编号")
	@TableId("ORG_NUM")
	private String orgNum;

	@ApiModelProperty(value = "客户名称")
	@TableId("CUSTOMER_NAME")
	private String customerName;

	@ApiModelProperty(value = "客户编号")
	@TableId("CUSTOMER_NUM")
	private String customerNum;

	@ApiModelProperty(value = "成员关系类型（CD000213)")
	@TableId("member_rel_type")
	private String memberRelType;

	@ApiModelProperty(value = "关系客户编号")
	@TableId("rel_customer_num")
	private String relCustomerNum;

	@ApiModelProperty(value = "客户关系类型")
	@TableId("rel_customer_type")
	private String relCustomerType;

	@ApiModelProperty(value = "客户关系类型(CD000016)")
	@TableId("rel_type")
	private String relType;

	@ApiModelProperty(value = "关系人名称")
	@TableId("cust_name")
	private String custName;

	@ApiModelProperty(value = "证件类型(CD000003)")
	@TableId("cert_type")
	private String certType;

	@ApiModelProperty(value = "证件号码")
	@TableId("cert_num")
	private String certNum;
}
