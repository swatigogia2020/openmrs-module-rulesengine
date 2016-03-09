package org.openmrs.module.rulesengine;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.rule.WeightBasedDoseRule;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class WeightBasedDoseRuleTest extends BaseModuleWebContextSensitiveTest {

    private WeightBasedDoseRule weightBasedDoseRule = new WeightBasedDoseRule();

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldThrowExceptionWeightNotAvailableWhenWeightObsDoesNotExist() {
        Dose calculatedDose;
        try {
            calculatedDose = weightBasedDoseRule.calculateDose("person_1032_uuid", 5.0);
        } catch (Exception e) {
            calculatedDose = null;
            assertEquals(e.getMessage(), "Weight is not available");
        }
        assertEquals(calculatedDose, null);
    }
}