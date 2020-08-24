package org.git.modules.drools.entity;


import lombok.Data;
import org.git.modules.drools.annotation.Drools;
import org.git.modules.drools.annotation.DroolsField;
import org.git.modules.drools.annotation.DroolsMethod;

@Drools(name = "规则测试处理对象",code = "rule999",explain = "用于规则的业务的规则定义及逻辑处理")
@Data
public class RuleTest {

	@DroolsField(name="参数1")
	private  String params1;
	@DroolsField(name="参数2")
	private String params2;

	@DroolsMethod(name="业务逻辑1")
	public void  exec1(){

	}

	@DroolsMethod(name="业务逻辑2")
	public void  exec2(){

	}




}
