package org.openmrs.module.rulesengine.rule;

import org.openmrs.module.rulesengine.domain.Dose;

/**
 * Created by apalani on 7/31/16.
 */
public interface DoseRule {
     Dose calculateDose(String drugName, String patientUuid, Double baseDose,String doseUnit, String orderSetName) throws Exception ;
    }
