package org.git.modules.drools.entity;


import lombok.Data;
import org.git.modules.drools.annotation.Drools;
import org.git.modules.drools.annotation.DroolsField;
import org.git.modules.drools.annotation.DroolsMethod;

@Drools(name = "请假业务处理对象",code = "rule002",explain = "用于请假的业务的规则定义及逻辑处理")
@Data
public class QueryParam extends BaseBizObj {

	@DroolsField(name="职务")
    private String duty ;
	@DroolsField(name="请假天数")
    private int days ;
	private String startDate;
	private String endDate;
	private String result ;

	@DroolsMethod(name="项目经理审批业务逻辑")
    public void  exec1(){
		System.out.println("项目经理审批成功");
		result = "项目经理审批成功";
	}

	@DroolsMethod(name="部门经理审批逻辑")
	public void  exec2(){
		System.out.println("部门经理审批成功");
		result = "部门经理审批成功";
	}

	@DroolsMethod(name="总经理审批逻辑")
	public void  exec3(){
		System.out.println("总经理审批成功");
		result = "总经理审批成功";
	}

	@DroolsMethod(name="董事长审批逻辑")
	public void  exec4(){
		System.out.println("董事长审批成功");
		result = "董事长审批成功";
	}
}
