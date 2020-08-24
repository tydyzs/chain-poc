package org.git.modules.clm.chart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.core.tool.node.INode;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;
import org.git.modules.clm.credit.entity.TbCrdSubcontract;

import java.util.ArrayList;
import java.util.List;

/**
 * 对外担保信息
 *
 * @author chenchuan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "按地区统计的额度数据", description = "按地区统计的额度数据")
public class CrdStatisAreaVO extends TbCrdStatisOrg implements INode {


	@ApiModelProperty(value = "地区ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private String id;

	@ApiModelProperty(value = "地区名称")
	@JsonSerialize(using = ToStringSerializer.class)
	private String areaName;

	@ApiModelProperty(value = "父地区ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private String parentId;

	@ApiModelProperty(value = "父地区名称")
	@JsonSerialize(using = ToStringSerializer.class)
	private String parentName;

	@ApiModelProperty(value = "子孙节点")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

//	@ApiModelProperty(value = "地区代码")
//	private String areaCode;
//
//	@ApiModelProperty(value = "地区名称")
//	private String areaName;
}
