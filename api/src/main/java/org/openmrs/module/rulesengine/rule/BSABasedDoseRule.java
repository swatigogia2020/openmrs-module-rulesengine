package org.openmrs.module.rulesengine.rule;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.EncounterService;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.BahmniMath;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("mg/m2")
public class BSABasedDoseRule implements DoseRule {

    public static Double calculateBSA(Double height, Double weight, Integer patientAgeInYears) {
        if (patientAgeInYears <= 15 && weight <= 40) {
            return Math.sqrt(weight * height / 3600);
        }
        return Math.pow(weight, 0.425) * Math.pow(height, 0.725) * 0.007184;
    }

    public Dose calculateDose(DosageRequest request) throws Exception {

        Patient patient = PatientService.getPatientByUuid(request.getPatientUuid());

        Encounter selectedEncounter = EncounterService.getLatestEncounterByPatient(patient);

        Date asOfDate = selectedEncounter.getEncounterDatetime();
        Integer ageInYears = BahmniMath.ageInYears(patient.getBirthdate(), asOfDate);

        Double height = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.HEIGHT);
        Double weight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT);
        Double bsa = calculateBSA(height, weight, ageInYears);

        double roundedUpValue = BahmniMath.getTwoDigitRoundUpValue(request.getBaseDose() * bsa);
        return new Dose(request.getDrugName(),roundedUpValue, Dose.DoseUnit.mg);
    }

}
