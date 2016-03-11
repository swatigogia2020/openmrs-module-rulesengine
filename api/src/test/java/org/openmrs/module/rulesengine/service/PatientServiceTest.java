package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class PatientServiceTest extends BaseModuleWebContextSensitiveTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldThrowPatientNotFoundExceptionWhenPatientDoesNotExist() {
        expectedException.expect(APIException.class);
        expectedException.expectMessage("Patient not found");
        PatientService.getPatientByUuid("random_uuid");
    }

    @Test
    public void shouldReturnPatientWhenPatientExist() {
        Patient patient = PatientService.getPatientByUuid("person_1055_uuid");
        assertEquals("person_1055_uuid", patient.getUuid());
    }

}
