package org.openmrs.module.rulesengine.engine;

import org.openmrs.module.rulesengine.domain.Dose;

/**
 * Created by apalani on 7/31/16.
 */
public interface RulesEngine {
    Dose calculateDose(String patientUUId, String drugName, Double baseDose, String doseUnit, String orderSetName) throws Exception;
}
