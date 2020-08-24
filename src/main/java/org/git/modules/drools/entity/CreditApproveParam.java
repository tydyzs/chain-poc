package org.git.modules.drools.entity;


import lombok.Data;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.drools.annotation.Drools;
import org.git.modules.drools.annotation.DroolsField;
import org.git.modules.drools.annotation.DroolsMethod;

@Drools(name = "授信审批规则", code = "rule003", explain = "用于授信审批规则的处理对象")
@Data
public class CreditApproveParam extends BaseBizObj {

	@DroolsField(name = "年龄")
	private int age;

	@DroolsField(name = "婚姻状况")
	private String marriage;

	@DroolsField(name = "最近24个月逾期的次数")
	private int overDueTimes;

	@DroolsField(name = "近15日内征信查询数")
	private int queryTimes;

	@DroolsField(name = "贷款“五级分类”为“关注”的账户数")
	private int loanGzNums;

	@DroolsField(name = "贷款“五级分类”为“次级”的账户数")
	private int loanCjNums;

	@DroolsField(name = "贷款“五级分类”为“可疑”的账户数")
	private int loanKyNums;

	@DroolsField(name = "贷款“五级分类”为“损失”的账户数")
	private int loanSsNums;

	@DroolsField(name = "在贷100万元以上贷款笔数")
	private int loan100MiNums;

	private String result;

	@DroolsMethod(name = "审批通过")
	public void exec1() {
		if (!StringUtil.isEmpty(result)) {
			return;
		}
		System.out.println("审批通过");
		result = "审批通过";
	}

	@DroolsMethod(name = "人工审批")
	public void exec2() {
		if (!StringUtil.isEmpty(result)) {
			return;
		}
		System.out.println("人工审批");
		result = "人工审批";
	}

	@DroolsMethod(name = "拒绝")
	public void exec3() {
		if (!StringUtil.isEmpty(result)) {
			return;
		}
		System.out.println("拒绝");
		result = "拒绝";
	}


}
