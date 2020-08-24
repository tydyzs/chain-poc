package org.git.modules.drools.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.git.common.constant.AppConstant;
import org.git.modules.drools.entity.QueryParam;
import org.git.modules.drools.entity.RuleResult;
import org.git.modules.drools.service.RuleEngineService;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/rule")
@Api(value = "规则测试", tags = "规则测试")
public class RuleController {

//    @Resource
//    private KieSession kieSession;
//    @Resource
//    private RuleEngineService ruleEngineService ;
//
//    @RequestMapping("/param")
//	@ApiOperation(value = "测试", notes = "测试")
//    public void param (){
//        QueryParam queryParam1 = new QueryParam() ;
//        queryParam1.setParamId("1");
//        queryParam1.setParamSign("+");
//        QueryParam queryParam2 = new QueryParam() ;
//        queryParam2.setParamId("2");
//        queryParam2.setParamSign("-");
//        // 入参
//        kieSession.insert(queryParam1) ;
//        kieSession.insert(queryParam2) ;
//        kieSession.insert(this.ruleEngineService) ;
//
//        // 返参
//        RuleResult resultParam = new RuleResult() ;
//        kieSession.insert(resultParam) ;
//        kieSession.fireAllRules() ;
//    }



}
