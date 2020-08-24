package org.git.modules.drools.service.impl;

import org.git.modules.drools.entity.BaseBizObj;
import org.git.modules.drools.entity.QueryParam;
import org.git.modules.drools.entity.RuleResult;
import org.git.modules.drools.service.RuleEngineService;
import org.git.modules.system.entity.DroolsRules;
import org.git.modules.system.service.IDroolsRulesService;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RuleEngineServiceImpl implements RuleEngineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineServiceImpl.class) ;

    @Autowired
    private IDroolsRulesService droolsRulesService;

    @Override
    public void executeAddRule(QueryParam param) {
        LOGGER.info("executeAddRule ===> 参数数据:"+param.getDuty()+";"+param.getDays());
    }

    @Override
    public void executeRemoveRule(QueryParam param) {
        LOGGER.info("executeRemoveRule === >参数数据:"+param.getDuty()+";"+param.getDays());
    }

	private static final String RULES_PATH = "droolRule/";
	@Override
	public void executeRules(BaseBizObj bizObj) throws IOException {
		String rules = "";
		DroolsRules ru = droolsRulesService.getDroolsRulesByCode(bizObj.getCode());
		if (ru != null && ru.getRules() != null) {
			rules = ru.getRules();
		}
		//KieServices：kie整体的入口,可以用来创建Container,resource,fileSystem等
		KieServices kieServices = KieServices.Factory.get();
		//KieFileSystem：一个完整的文件系统,包括资源和组织结构
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write("src/main/resources/"+bizObj.getCode()+".drl",rules.getBytes("UTF-8"));

		//构建出这个虚拟文件系统，自动去构建KieModule。
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

		Results results = kieBuilder.getResults();
		if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new IllegalStateException("### errors ###");
		}
		// KieContainer就是一个KieBase的容器
		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		//  KieBase就是一个知识仓库，包含了若干的规则、流程、方法等
		KieBase kieBase = kieContainer.getKieBase();

		// KieSession就是一个跟Drools引擎打交道的会话
		KieSession ksession = kieBase.newKieSession();
		ksession.insert(bizObj);
		ksession.fireAllRules();
	}
}
