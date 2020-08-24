package org.git.modules.desk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.desk.entity.Notice;

/**
 * 通知公告视图类
 *
 * @author Chill
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "通知公告查询结果", description = "通知公告查询结果")
public class NoticeVO extends Notice {

	@ApiModelProperty(value = "通知类型名称")
	private String categoryName;

	@ApiModelProperty(value = "发布人名称")
	private String createUserName;

	@ApiModelProperty(value = "发布机构名称")
	private String createDeptName;

}
