package org.openmrs.module.rulesengine.rule;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.EncounterService;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.BahmniMath;

import java.util.Date;

public class BSABasedDoseRule {

    public static Double calculateBSA(Double height, Double weight, Integer patientAgeInYears) {
        if (patientAgeInYears <= 15 && weight <= 40) {
            return Math.sqrt(weight * height / 3600);
        }
        return Math.pow(weight, 0.425) * Math.pow(height, 0.725) * 0.007184;
    }

    public static Dose calculateDose(String patientUuid, Double baseDose) throws Exception {

        Patient patient = PatientService.getPatientByUuid(patientUuid);

        Encounter selectedEncounter = EncounterService.getLatestEncounterByPatient(patient);

        Date asOfDate = selectedEncounter.getEncounterDatetime();
        Integer ageInYears = BahmniMath.ageInYears(patient.getBirthdate(), asOfDate);

        Double height = ObservationService.getLatestHeight(patient);
        Double weight = ObservationService.getLatestWeight(patient);
        Double bsa = calculateBSA(height, weight, ageInYears);

        double roundedUpValue = BahmniMath.getTwoDigitRoundUpValue(baseDose * bsa);
        return new Dose(roundedUpValue, Dose.DoseUnit.mg);
    }

}
