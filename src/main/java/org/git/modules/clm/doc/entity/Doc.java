package org.git.modules.clm.doc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("tb_doc_release")//mybatis-plus配置数据库-表名
public class Doc implements Serializable {

	@TableId(value = "id", type = IdType.UUID)//mybatis-plus主键不是自动生成的，需要配置主键生成的算法
	private String id;
	/**
	 * 文档名称
	 */
	private String docName;
	/**
	 * 文档根目录名称
	 */
	private String docRootName;
	/**
	 * 文档类型名称
	 */
	private String docTypeName;
	/**
	 * 文档类型编码
	 */
	private String docTypeCode;
	/**
	 * 文档后缀
	 */
	private String docSuffix;
	/**
	 * 文档备注
	 */
	private String docBz;
	/**
	 * 文档权限
	 */
	private String docAuth;
	/**
	 * 创建用户编码
	 */
	private String userNum;
	/**
	 * 创建机构编码
	 */
	private String orgNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 是否已发布
	 */
	private String isRelease;
	/**
	 * 删除标志
	 */
	@TableLogic//添加此注解，mybatis-plus删除变为逻辑删除,mybatis-plus的查询也会过滤
	private String isDelete;
}
