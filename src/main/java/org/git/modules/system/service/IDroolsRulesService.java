package org.git.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.system.entity.DroolsRules;

public interface IDroolsRulesService extends IService<DroolsRules> {



	IPage<DroolsRules> getDroolsRulesPage(IPage<DroolsRules> page, DroolsRules rules);

	DroolsRules getDroolsRulesByCode(String code);

	DroolsRules createRuleDrl(String ruleId);

}
