package org.openmrs.module.rulesengine.rule;

import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;

/**
 * Created by apalani on 7/31/16.
 */
public interface DoseRule {
     Dose calculateDose(DosageRequest request) throws Exception ;
    }
