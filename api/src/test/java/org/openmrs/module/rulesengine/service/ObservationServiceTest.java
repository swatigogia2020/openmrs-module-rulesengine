package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.junit.Assert.assertEquals;

public class ObservationServiceTest extends BaseModuleWebContextSensitiveTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        executeDataSet("doseRuleTestData.xml");
    }

    @Test
    public void shouldThrowExceptionHeightNotAvailableWhenHeightObsDoesNotExist() throws Exception {
        expectedException.expect(APIException.class);
        expectedException.expectMessage("Observation for Height is not captured.");
        Patient patient = Context.getPatientService().getPatientByUuid("person_1031_uuid");
        ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT);
    }

    @Test
    public void shouldReturnHeightOfThePatient() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Double latestHeight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT);
        assertEquals(170.0, latestHeight, 0.0);
    }

    @Test
    public void shouldThrowExceptionWeightNotAvailableWhenWeightObsDoesNotExist() throws Exception {
        expectedException.expect(APIException.class);
        expectedException.expectMessage("Observation for Weight is not captured.");
        Patient patient = Context.getPatientService().getPatientByUuid("person_1032_uuid");
        ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT);
    }

    @Test
    public void shouldReturnWeightOfThePatient() throws Exception {
        Patient patient = Context.getPatientService().getPatientByUuid("person_1055_uuid");
        Double latestWeight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT);
        assertEquals(80.0, latestWeight, 0.0);
    }

}
