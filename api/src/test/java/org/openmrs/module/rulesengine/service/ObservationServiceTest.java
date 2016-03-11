package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class ObservationServiceTest extends BaseModuleWebContextSensitiveTest {

    private ObservationService observationService = new ObservationService();

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldThrowExceptionHeightNotAvailableWhenHeightObsDoesNotExist() {
        Double height;
        try {
            Patient patient = Context.getPatientService().getPatientByUuid("person_1031_uuid");
            height = observationService.getLatestHeight(patient);
        } catch (Exception e) {
            height = null;
            assertEquals("Height is not available", e.getMessage());
        }
        assertEquals(height, null);
    }

    @Test
    public void shouldReturnHeightOfThePatient() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Double latestHeight = observationService.getLatestHeight(patient);
        assertEquals(170.0, latestHeight, 0.0);
    }

    @Test
    public void shouldThrowExceptionWeightNotAvailableWhenWeightObsDoesNotExist() {
        Double weight;
        try {
            Patient patient = Context.getPatientService().getPatientByUuid("person_1032_uuid");
            weight = observationService.getLatestWeight(patient);
        } catch (Exception e) {
            weight = null;
            assertEquals("Weight is not available", e.getMessage());
        }
        assertEquals(weight, null);
    }

    @Test
    public void shouldReturnWeightOfThePatient() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Double latestWeight = observationService.getLatestWeight(patient);
        assertEquals(80.0, latestWeight, 0.0);
    }

}
