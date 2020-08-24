package org.git.modules.clm.doc.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.git.modules.clm.doc.entity.Doc;

/**
 * 通知公告视图类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocVO extends Doc {
	/**
	 * 创建用户名称
	 *
	 */
	private String createUserName;
	/**
	 * 创建部门名称
	 *
	 */
	private String createDeptName;
	/**
	 * 是否自己创建(自己上传的文件才可以删除）
	 *
	 */
	private String isMe;
	/**
	 * 格式化的日期
	 * */
	private String createDate;
}
