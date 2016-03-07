package org.openmrs.module.rulesengine.service;

import org.openmrs.module.rulesengine.contract.Dose;

public interface DoseRuleService {
    Dose calculateDose(String patientUuid, Double baseDose, String calculatedDoseUnit) throws Exception;
}
