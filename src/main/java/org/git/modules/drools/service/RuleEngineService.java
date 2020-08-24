package org.git.modules.drools.service;

import org.git.modules.drools.entity.BaseBizObj;
import org.git.modules.drools.entity.QueryParam;
import org.git.modules.drools.entity.RuleResult;

import java.io.IOException;

public interface RuleEngineService {
    void executeAddRule(QueryParam param) ;
    void executeRemoveRule(QueryParam param) ;
	void executeRules(BaseBizObj bizObj) throws IOException;
}
