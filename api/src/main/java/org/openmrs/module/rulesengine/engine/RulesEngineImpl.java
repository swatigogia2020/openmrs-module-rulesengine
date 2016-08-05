package org.openmrs.module.rulesengine.engine;

import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.DoseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RulesEngineImpl implements RulesEngine {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Dose calculateDose(DosageRequest request) throws Exception {
        DoseRule rule= applicationContext.getBean(request.getDosingRule(),DoseRule.class);
        return rule.calculateDose(request);
    }

}
