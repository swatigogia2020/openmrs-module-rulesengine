package org.openmrs.module.rulesengine.rule;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.EncounterService;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.CalculationsUtil;

import java.util.Date;

public class BSABasedDoseRule {

    private final ObservationService observationService = new ObservationService();
    private final EncounterService encounterService = new EncounterService();
    private final PatientService patientService = new PatientService();

    public Dose calculateDose(String patientUuid, Double baseDose) throws Exception {

        Patient patient = patientService.getPatientByUuid(patientUuid);

        Encounter selectedEncounter = encounterService.getLatestEncounterByPatient(patient);

        Date asOfDate = selectedEncounter.getEncounterDatetime();
        Integer ageInYears = CalculationsUtil.ageInYears(patient.getBirthdate(), asOfDate);

        Double height = observationService.getLatestHeight(patient);
        Double weight = observationService.getLatestWeight(patient);
        Double bsa = CalculationsUtil.calculateBSA(height, weight, ageInYears);

        double roundedUpValue = CalculationsUtil.getTwoDigitRoundUpValue(baseDose * bsa);
        return new Dose(roundedUpValue, Dose.DoseUnit.mg);
    }

}
