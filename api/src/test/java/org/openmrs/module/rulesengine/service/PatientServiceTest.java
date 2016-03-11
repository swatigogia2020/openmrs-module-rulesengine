package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class PatientServiceTest extends BaseModuleWebContextSensitiveTest {

    private PatientService patientService = new PatientService();

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldThrowPatientNotFoundExceptionWhenPatientDoesNotExist() {
        Patient patient;
        try {
            patient = patientService.getPatientByUuid("random_uuid");
        } catch (Exception e) {
            patient = null;
            assertEquals("Patient not found", e.getMessage());
        }
        assertEquals(patient, null);
    }

    @Test
    public void shouldReturnPatientWhenPatientExist() {
        Patient patient = patientService.getPatientByUuid("person_1055_uuid");
        assertEquals("person_1055_uuid", patient.getUuid());
    }

}
