package org.openmrs.module.rulesengine.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Context.class)
public class EncounterServiceTest {

    @Mock
    private org.openmrs.api.EncounterService openmrsEncounterService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    Patient patient;
    ArrayList<Encounter> encounters;

    @Before
    public void setUp() throws Exception {
        patient =  new Patient(1054);
        Encounter encounter = new Encounter(1055);
        encounter.setEncounterDatetime(new Date());
        encounter.setUuid("encounter_1055_uuid");
        encounters = new ArrayList<>();
        encounters.add(encounter);
        EncounterType encounterType = new EncounterType("En", "RegEncounter");

        PowerMockito.mockStatic(Context.class);
        PowerMockito.when(Context.getEncounterService()).thenReturn(openmrsEncounterService);
        PowerMockito.when(openmrsEncounterService.getEncounterType("REG")).thenReturn(encounterType);
    }

    @Test
    public void shouldThrowNoEncounterFoundExceptionWhenNoEncounterExist() {
        expectedException.expect(APIException.class);
        expectedException.expectMessage("No Encounter found");
        ArrayList<Encounter> encounters = new ArrayList<>();

        PowerMockito.when(openmrsEncounterService.getEncounters(any(EncounterSearchCriteria.class))).thenReturn(encounters);
        EncounterService.getLatestEncounterByPatient(patient);
    }

    @Test
    public void shouldReturnEncounterWhenThereIsAnEncounterForThePatient(){
        PowerMockito.when(openmrsEncounterService.getEncounters(any(EncounterSearchCriteria.class))).thenReturn(encounters);

        Encounter encounter = EncounterService.getLatestEncounterByPatient(patient);
        assertEquals("encounter_1055_uuid", encounter.getUuid());
    }

    @Test
    public void shouldGetEncountersOfEncounterServiceBeCalledWithEncounterSearchCriteriaObject() throws Exception {
        PowerMockito.when(openmrsEncounterService.getEncounters(any(EncounterSearchCriteria.class))).thenReturn(encounters);

        Encounter encounter = EncounterService.getLatestEncounterByPatient(patient);
        verify(openmrsEncounterService).getEncounters(any(EncounterSearchCriteria.class));
    }
}
