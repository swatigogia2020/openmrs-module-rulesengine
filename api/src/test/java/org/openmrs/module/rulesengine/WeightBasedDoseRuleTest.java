package org.openmrs.module.rulesengine;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.rulesengine.contract.Dose;
import org.openmrs.module.rulesengine.rule.WeightBasedDoseRule;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class WeightBasedDoseRuleTest extends BaseModuleWebContextSensitiveTest {

    @Autowired
    private WeightBasedDoseRule weightBasedDoseRule;

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