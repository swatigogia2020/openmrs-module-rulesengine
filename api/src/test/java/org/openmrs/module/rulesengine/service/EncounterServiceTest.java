package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class EncounterServiceTest extends BaseModuleWebContextSensitiveTest {
    private EncounterService encounterService = new EncounterService();

    @Before
    public void setUp() throws Exception {
        executeDataSet("DoseRuleTestData.xml");
    }

    @Test
    public void shouldThrowNoEncounterFoundExceptionWhenNoEncounterExist() {
        Encounter encounter;
        try {
            Patient patient = Context.getPatientService().getPatientByUuid("person_1050_uuid");
            encounter = encounterService.getLatestEncounterByPatient(patient);
        } catch (Exception e) {
            encounter = null;
            assertEquals("No Encounter found", e.getMessage());
        }
        assertEquals(null, encounter);
    }

    @Test
    public void shouldReturnEncounterWhenThereIsAnEncounterForThePatient(){
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Encounter encounter = encounterService.getLatestEncounterByPatient(patient);
        assertEquals("encounter_1055_uuid", encounter.getUuid());
    }
}
