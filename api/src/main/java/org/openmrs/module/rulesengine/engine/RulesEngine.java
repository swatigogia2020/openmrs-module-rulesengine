package org.openmrs.module.rulesengine.engine;

import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.DosageRule;

public interface RulesEngine {
    Dose calculateDose(DosageRequest request) throws Exception;
    String[] getRulesRegistered() throws Exception;
    String[] getRuleNames();
    DosageRule getRuleObject(String ruleName);
}
