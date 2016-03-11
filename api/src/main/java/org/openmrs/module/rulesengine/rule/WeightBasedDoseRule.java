package org.openmrs.module.rulesengine.rule;

import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.BahmniMath;

public class WeightBasedDoseRule {

    public static Dose calculateDose(String patientUuid, Double baseDose) throws Exception {
        Patient patient = PatientService.getPatientByUuid(patientUuid);

        Double weight = ObservationService.getLatestWeight(patient);

        double roundedUpDoseValue = BahmniMath.getTwoDigitRoundUpValue(baseDose * weight);
        return new Dose(roundedUpDoseValue, Dose.DoseUnit.mg);
    }

}
