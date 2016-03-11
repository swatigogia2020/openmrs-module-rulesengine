package org.openmrs.module.rulesengine.rule;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;


public class BSABasedDoseRuleTest extends BaseModuleWebContextSensitiveTest {

    private BSABasedDoseRule bsaBasedDoseRule = new BSABasedDoseRule();

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldReturnDoseWhenWeightObservationExistForLatestEncounter() throws Exception {
        Dose dose = bsaBasedDoseRule.calculateDose("person_1055_uuid", 10.0);
        assertEquals(19.15, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());

        dose = bsaBasedDoseRule.calculateDose("person_1055_uuid", 15.0);
        assertEquals(28.73, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());
    }

}
