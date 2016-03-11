package org.openmrs.module.rulesengine.rule;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.EncounterService;
import org.openmrs.module.rulesengine.service.ObservationService;

import java.math.BigDecimal;
import java.util.Date;

public class BSABasedDoseRule {

    private final ObservationService observationService = new ObservationService();
    private final EncounterService encounterService = new EncounterService();

    public Dose calculateDose(String patientUuid, Double baseDose) throws Exception {

        Patient patient = Context.getPatientService().getPatientByUuid(patientUuid);

        Encounter selectedEncounter = encounterService.getLatestEncounterByPatient(patient);

        Integer ageInYears = ageInYears(patient, selectedEncounter.getEncounterDatetime());

        Double height = observationService.getLatestHeight(patient, selectedEncounter);
        Double weight = observationService.getLatestWeight(patient, selectedEncounter);
        Double bsa = calculateBSA(height, weight, ageInYears);

        double roundedUpValue = new BigDecimal(bsa * baseDose).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return new Dose(roundedUpValue, Dose.DoseUnit.mg);
    }

    private Integer ageInYears(Patient patient, Date asOfDate) {
        Date birthdate = patient.getBirthdate();
        return Years.yearsBetween(new LocalDate(birthdate), new LocalDate(asOfDate)).getYears();
    }

    private Double calculateBSA(Double height, Double weight, Integer patientAgeInYears) {
        if (patientAgeInYears <= 15 && weight <= 40) {
            return Math.sqrt(weight * height / 3600);
        }
        return Math.pow(weight, 0.425) * Math.pow(height, 0.725) * 0.007184;
    }

}
