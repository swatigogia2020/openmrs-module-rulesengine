package org.openmrs.module.rulesengine;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.rulesengine.contract.Dose;
import org.openmrs.module.rulesengine.service.DoseRuleService;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class DoseRuleServiceTest extends BaseModuleWebContextSensitiveTest {

    private DoseRuleService doseRuleService;

    @Before
    public void setUp() throws Exception {
        executeDataSet("doseRuleServiceTestData.xml");
        doseRuleService = Context.getService(DoseRuleService.class);
    }

    @Test
    public void shouldGetCalculatedDoseForAGivenRule() throws Exception {
        Dose dosage = doseRuleService.calculateDose("person_1024_uuid", 5.0, "mg/m2");
        assertEquals(8.65, dosage.getValue(), 0.01);
        assertEquals(Dose.DoseUnit.mg, dosage.getDoseUnit());

        dosage = doseRuleService.calculateDose("person_1024_uuid", 5.0, "mg/kg");
        assertEquals(350.0, dosage.getValue(), 0.01);
        assertEquals(Dose.DoseUnit.mg, dosage.getDoseUnit());
    }

    @Test
    public void shouldGetCalculatedDoseForTheLatestObservations() throws Exception {
        Dose dosage = doseRuleService.calculateDose("person_1030_uuid", 5.0, "mg/m2");
        assertEquals(9.58, dosage.getValue(), 0.01);
        assertEquals(Dose.DoseUnit.mg, dosage.getDoseUnit());

        dosage = doseRuleService.calculateDose("person_1030_uuid", 5.0, "mg/kg");
        assertEquals(400.0, dosage.getValue(), 0.01);
        assertEquals(Dose.DoseUnit.mg, dosage.getDoseUnit());
    }

}