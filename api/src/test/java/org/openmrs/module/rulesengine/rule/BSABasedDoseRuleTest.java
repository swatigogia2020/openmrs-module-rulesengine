package org.openmrs.module.rulesengine.rule;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;


public class BSABasedDoseRuleTest extends BaseModuleWebContextSensitiveTest {

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldReturnDoseWhenWeightObservationExistForLatestEncounter() throws Exception {
        Dose dose = BSABasedDoseRule.calculateDose("person_1055_uuid", 10.0);
        assertEquals(19.15, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());

        dose = BSABasedDoseRule.calculateDose("person_1055_uuid", 15.0);
        assertEquals(28.73, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());
    }

    @Test
    public void calculateBsaShouldReturnCorrectValue() throws Exception{
        Double bsa = BSABasedDoseRule.calculateBSA(170.0, 70.0, 23);
        assertEquals(1.809,bsa,0.001);

        bsa = BSABasedDoseRule.calculateBSA(120.0, 35.0, 10);
        assertEquals(1.080,bsa,0.001);

        bsa = BSABasedDoseRule.calculateBSA(120.0, 35.0, 20);
        assertEquals(1.047,bsa,0.001);

        bsa = BSABasedDoseRule.calculateBSA(120.0, 41.0, 15);
        assertEquals(1.119,bsa,0.001);
    }
}
