package org.openmrs.module.rulesengine.rule;

import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;

public interface DosageRule {
     Dose calculateDose(DosageRequest request) throws Exception ;
    }
