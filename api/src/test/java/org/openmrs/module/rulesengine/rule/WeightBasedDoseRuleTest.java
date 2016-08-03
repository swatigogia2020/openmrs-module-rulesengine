package org.openmrs.module.rulesengine.rule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.engine.DosageConverter;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PatientService.class, ObservationService.class})
public class WeightBasedDoseRuleTest  {

    @InjectMocks
    WeightBasedDoseRule weightBasedDoseRule= new WeightBasedDoseRule();

    @Mock
    private DosageConverter dosageConverter;

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
        when(dosageConverter.convertDosageByPatient("paracetamol",20,50.0,10.0,"testorderset"))
                .thenReturn(new Dose("paracetamol",400, Dose.DoseUnit.mg));

        DosageRequest dosageRequest = new DosageRequest("paracetamol","person_1055_uuid", 10.0, "mg/kg","testorderset");

        Dose dose = weightBasedDoseRule.calculateDose(dosageRequest);
        assertEquals(400, dose.getValue(), 0.0);
        assertEquals(Dose.DoseUnit.mg, dose.getDoseUnit());
        verify(dosageConverter,times(1)).convertDosageByPatient("paracetamol",20,50.0,10,"testorderset");
    }
}