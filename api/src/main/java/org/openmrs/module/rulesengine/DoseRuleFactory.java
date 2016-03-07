package org.openmrs.module.rulesengine;

import org.openmrs.module.rulesengine.contract.Dose;
import org.openmrs.module.rulesengine.rule.BSABasedDoseRule;
import org.openmrs.module.rulesengine.rule.DoseRule;
import org.openmrs.module.rulesengine.rule.WeightBasedDoseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DoseRuleFactory {

    @Autowired
    private ApplicationContext appContext;

    public static final Map<Dose.CalculatedDoseUnit, Class<? extends DoseRule>> DOSE_RULE_MAP = new HashMap<Dose.CalculatedDoseUnit, Class<? extends DoseRule>>() {{
        this.put(Dose.CalculatedDoseUnit.mg_per_kg, WeightBasedDoseRule.class);
        this.put(Dose.CalculatedDoseUnit.mg_per_m2, BSABasedDoseRule.class);
    }};

    public DoseRule getRule(Dose.CalculatedDoseUnit calculatedDoseUnit) {
        return appContext.getBean(DOSE_RULE_MAP.get(calculatedDoseUnit));
    }

}