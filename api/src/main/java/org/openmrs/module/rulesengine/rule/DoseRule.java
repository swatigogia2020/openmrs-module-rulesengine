package org.openmrs.module.rulesengine.rule;

import org.openmrs.module.rulesengine.contract.Dose;

public interface DoseRule {

    Dose calculateDose(String patientUuid, Double baseDose) throws Exception;
}
