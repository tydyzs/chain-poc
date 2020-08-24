package org.git.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.system.entity.DroolsRules;

import java.util.List;

public interface DroolsRulesMapper extends BaseMapper<DroolsRules> {

	List<DroolsRules> getDroolsRulesPage(IPage<DroolsRules> page, DroolsRules rules);
}
