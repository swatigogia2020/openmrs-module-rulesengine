package org.openmrs.module.rulesengine.rule

import org.openmrs.module.rulesengine.domain.DosageRequest
import org.openmrs.module.rulesengine.domain.Dose
import org.openmrs.module.rulesengine.domain.RuleName

@RuleName(name = "testrule")
public class TestDosageRule implements DosageRule {

    public Dose calculateDose(DosageRequest request) throws Exception {
       return new Dose(request.drugName,100,Dose.DoseUnit.mg);
    }

}