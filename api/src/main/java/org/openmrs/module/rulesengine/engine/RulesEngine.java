package org.openmrs.module.rulesengine.engine;

import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;

public interface RulesEngine {
    Dose calculateDose(DosageRequest request) throws Exception;
}
