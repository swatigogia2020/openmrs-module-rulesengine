package org.openmrs.module.rulesengine.service;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.rulesengine.DoseRuleFactory;
import org.openmrs.module.rulesengine.contract.Dose;
import org.openmrs.module.rulesengine.rule.DoseRule;
import org.springframework.beans.factory.annotation.Autowired;

public class DoseRuleServiceImpl extends BaseOpenmrsService implements DoseRuleService {

    @Autowired
    private DoseRuleFactory doseRuleFactory;

    @Override
    public Dose calculateDose(String patientUuid, Double baseDose, String stringDoseUnit) throws Exception {
        Dose.CalculatedDoseUnit calculatedDoseUnit = Dose.CalculatedDoseUnit.getConstant(stringDoseUnit);
        if(null== calculatedDoseUnit){
            String errMessage = "Dosing Rule not found for given doseUnits (" + stringDoseUnit + ").";
            throw new APIException(errMessage);
        }
        DoseRule doseRule = doseRuleFactory.getRule(calculatedDoseUnit);
        return doseRule.calculateDose(patientUuid, baseDose);
    }

}
