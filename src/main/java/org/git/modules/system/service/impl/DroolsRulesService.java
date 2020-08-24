package org.git.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.core.mp.support.Condition;
import org.git.modules.system.entity.DroolsDetail;
import org.git.modules.system.entity.DroolsRules;
import org.git.modules.system.mapper.DroolsRulesMapper;
import org.git.modules.system.service.IDroolsDetailService;
import org.git.modules.system.service.IDroolsRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroolsRulesService extends ServiceImpl<DroolsRulesMapper, DroolsRules> implements IDroolsRulesService {

	@Autowired
	private IDroolsDetailService droolsDetailService;
	@Override
	public IPage<DroolsRules> getDroolsRulesPage(IPage<DroolsRules> page, DroolsRules rules) {
		return page.setRecords(baseMapper.getDroolsRulesPage(page,rules));
	}

	@Override
	public DroolsRules getDroolsRulesByCode(String code) {
		return baseMapper.selectOne(Wrappers.<DroolsRules>query().lambda().eq(DroolsRules::getCode,code));
	}

	@Override
	public DroolsRules createRuleDrl(String ruleId) {

		DroolsRules droolsRules = this.getById(ruleId);
		DroolsDetail droolsDetail = new DroolsDetail();
		droolsDetail.setRuleId(ruleId);
		QueryWrapper<DroolsDetail> queryWrapper = Condition.getQueryWrapper(droolsDetail);
		List<DroolsDetail> list = droolsDetailService.list(queryWrapper.lambda().eq(DroolsDetail::getRuleId, droolsDetail.getRuleId()));

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("package org.git.droolRule ;\r\n");
		stringBuffer.append("import "+droolsRules.getBizClassPath()+";\r\n");
		stringBuffer.append("dialect  \"java\" \r\n");
		int i = 1;
		for (DroolsDetail dd :list ) {
			stringBuffer.append("// \""+dd.getRuleName()+"\" \r\n");
			stringBuffer.append("rule \""+droolsRules.getCode()+"_"+i+"\" \r\n");
			stringBuffer.append("    salience "+dd.getRulePriority()+" \r\n");
			String objName = droolsRules.getBizClassName().toLowerCase();
			stringBuffer.append("    when "+objName+" : "+droolsRules.getBizClassName()+"("+dd.getRuleCondition()+") \r\n");
			stringBuffer.append("    then \r\n");
			stringBuffer.append("        "+objName+"."+ dd.getRuleExpression()+"(); \r\n");
			stringBuffer.append("end \r\n");
			i++;
		}
		droolsRules.setRules(stringBuffer.toString());
		updateById(droolsRules);
		return  droolsRules;
	}


}
