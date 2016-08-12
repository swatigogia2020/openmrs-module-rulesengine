package org.openmrs.module.rulesengine.rule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PatientService.class, ObservationService.class})
public class WeightBasedDoseRuleTest  {

    @InjectMocks
    WeightBasedDosageRule weightBasedDoseRule= new WeightBasedDosageRule();

    @Before
    public void setUp() throws Exception {
        mockStatic(PatientService.class);
        mockStatic(ObservationService.class);
    }

    @Test
    public void shouldReturnCalculatedDoseBasedOnWeight() throws Exception {
        Patient patient= mock(Patient.class);
        when(patient.getAge()).thenReturn(20);
        when(PatientService.getPatientByUuid(anyString())).thenReturn(patient);
        when(ObservationService.getLatestObsValueNumeric(any(Patient.class),any(ObservationService.ConceptRepo.class)))
                .thenReturn(50.0);

        DosageRequest dosageRequest = new DosageRequest("paracetamol","person_1055_uuid", 10.0, "mg/kg","testorderset","mg/kg");

        Dose dose = weightBasedDoseRule.calculateDose(dosageRequest);
        assertEquals(500, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());
    }
}