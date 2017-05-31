package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.rulesengine.RulesEngineBaseIT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ObservationServiceTest extends RulesEngineBaseIT {

    @Before
    public void setUp() throws Exception {
        executeDataSet("doseRuleTestData.xml");
    }

    @Test
    public void shouldReturnNullWhenHeightObsDoesNotExistInAnyVisit() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1031_uuid");
        Double latestObsValueNumeric = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT, null);
        assertNull(latestObsValueNumeric);
    }

    @Test
    public void shouldReturnNullWhenHeightObsDoesNotExistInGivenVisit() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_2000_uuid");
        Double latestObsValueNumeric = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT, "visit_uuid_2001");
        assertNull(latestObsValueNumeric);
    }

    @Test
    public void shouldReturnLatestHeightOfThePatientFromAllVisits() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_2000_uuid");
        Double latestHeight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT, null);
        assertEquals(175.0, latestHeight, 0.0);
    }

    @Test
    public void shouldReturnLatestHeightOfThePatientFromGivenVisit() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_2000_uuid");
        Double latestHeight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT, "visit_uuid_2000");
        assertEquals(170.0, latestHeight, 0.0);
    }

    @Test
    public void shouldReturnNullIfWhenWeightObsDoesNotExist() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1032_uuid");
        Double latestObsValueNumeric = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT, null);
        assertNull(latestObsValueNumeric);
    }

    @Test
    public void shouldReturnWeightOfThePatient() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Double latestWeight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT, null);
        assertEquals(80.0, latestWeight, 0.0);
    }

}
