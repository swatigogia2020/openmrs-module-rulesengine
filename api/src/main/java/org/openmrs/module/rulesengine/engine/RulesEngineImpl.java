package org.openmrs.module.rulesengine.engine;

import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.DoseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by apalani on 7/31/16.
 */
@Component
public class RulesEngineImpl implements RulesEngine {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Dose calculateDose(String patientUUId, String drugName, Double baseDose, String doseUnit, String orderSetName) throws Exception {
        DoseRule rule= applicationContext.getBean(doseUnit,DoseRule.class);
        return rule.calculateDose(drugName, patientUUId, baseDose,doseUnit, orderSetName);
    }

}
