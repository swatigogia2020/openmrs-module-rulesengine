package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class EncounterServiceTest extends BaseModuleWebContextSensitiveTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        executeDataSet("doseRuleTestData.xml");
    }

    @Test
    public void shouldThrowNoEncounterFoundExceptionWhenNoEncounterExist() {
        expectedException.expect(APIException.class);
        expectedException.expectMessage("No Encounter found");
        Patient patient = Context.getPatientService().getPatientByUuid("person_1050_uuid");
        EncounterService.getLatestEncounterByPatient(patient);
    }

    @Test
    public void shouldReturnEncounterWhenThereIsAnEncounterForThePatient(){
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Encounter encounter = EncounterService.getLatestEncounterByPatient(patient);
        assertEquals("encounter_1055_uuid", encounter.getUuid());
    }
}
