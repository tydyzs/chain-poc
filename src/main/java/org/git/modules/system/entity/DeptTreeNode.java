package org.git.modules.system.entity;

import org.git.core.tool.node.TreeNode;

public class DeptTreeNode extends TreeNode {
	/**
	 * 机构类型 1省联社，2成员行
	 */
	private String deptCategory;

	/**
	 * 机构级别
	 */
	private String deptLevel;

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getDeptCategory() {
		return deptCategory;
	}

	public void setDeptCategory(String deptCategory) {
		this.deptCategory = deptCategory;
	}
}
