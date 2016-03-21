package org.openmrs.module.rulesengine.rule;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.WeightBasedDoseRule;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class WeightBasedDoseRuleTest extends BaseModuleWebContextSensitiveTest {

    @Before
    public void setUp() throws Exception {
        executeDataSet("doseRuleTestData.xml");
    }

    @Test
    public void shouldReturnCalculatedDoseBasedOnWeight() throws Exception {
        Dose dose = WeightBasedDoseRule.calculateDose("person_1055_uuid", 5.0);
        assertEquals(400, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());

        dose = WeightBasedDoseRule.calculateDose("person_1055_uuid", 10.0);
        assertEquals(800, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());
    }
}