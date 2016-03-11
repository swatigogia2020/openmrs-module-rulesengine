package org.openmrs.module.rulesengine.rule;

import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.CalculationsUtil;

public class WeightBasedDoseRule {

    private final ObservationService observationService = new ObservationService();
    private final PatientService patientService = new PatientService();

    public Dose calculateDose(String patientUuid, Double baseDose) throws Exception {
        Patient patient = patientService.getPatientByUuid(patientUuid);

        Double weight = observationService.getLatestWeight(patient);

        double roundedUpDoseValue = CalculationsUtil.getTwoDigitRoundUpValue(baseDose * weight);
        return new Dose(roundedUpDoseValue, Dose.DoseUnit.mg);
    }

}
