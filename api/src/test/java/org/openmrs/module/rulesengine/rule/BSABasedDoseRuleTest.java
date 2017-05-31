package org.openmrs.module.rulesengine.rule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.BahmniMath;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PatientService.class, ObservationService.class, BahmniMath.class})
public class BSABasedDoseRuleTest  {

    @Before
    public void setUp() throws Exception {
        mockStatic(PatientService.class);
        mockStatic(ObservationService.class);
        mockStatic(BahmniMath.class);
    }

    @Test
    public void shouldReturnDoseWhenWeightObservationExistForLatestEncounter() throws Exception {
        Patient patient = mock(Patient.class);
        when(patient.getBirthdate()).thenReturn(new Date());

        PatientService patientService= mock(PatientService.class);
        when(patientService.getPatientByUuid(any(String.class))).thenReturn(patient);

        ObservationService observationService = mock(ObservationService.class);
        when(observationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT, "visit_uuid")).thenReturn(189.0);
        when(observationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT, "visit_uuid")).thenReturn(69.0);

        BahmniMath bahmniMath = mock(BahmniMath.class);
        when(bahmniMath.ageInYears(any(Date.class),any(Date.class))).thenReturn(40);
        when(bahmniMath.getTwoDigitRoundUpValue(any(Double.class))).thenReturn(150.0);

        DosageRequest dosageRequest = new DosageRequest(
                "paracetamol",
                "person_1055_uuid",
                10.0,
                "mg/kg",
                "testorderset",
                "test",
                "visit_uuid"
        );

        Dose dose = new BSABasedDosageRule().calculateDose(dosageRequest);

        assertEquals(150, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());
    }

    @Test
    public void calculateBsaShouldReturnCorrectValue() throws Exception{
        Double bsa = BSABasedDosageRule.calculateBSA(170.0, 70.0, 23);
        assertEquals(1.809,bsa,0.001);

        bsa = BSABasedDosageRule.calculateBSA(120.0, 35.0, 10);
        assertEquals(1.080,bsa,0.001);

        bsa = BSABasedDosageRule.calculateBSA(120.0, 35.0, 20);
        assertEquals(1.047,bsa,0.001);

        bsa = BSABasedDosageRule.calculateBSA(120.0, 41.0, 15);
        assertEquals(1.119,bsa,0.001);
    }
}
