package org.openmrs.module.rulesengine.rule;

import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.engine.DosageConverter;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mg/kg")
public class WeightBasedDoseRule implements DoseRule {

    @Autowired
    private DosageConverter dosageConverter;

    public Dose calculateDose(DosageRequest request) throws Exception {
        Patient patient = PatientService.getPatientByUuid(request.getPatientUuid());

        Double weight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT);

        return dosageConverter.convertDosageByPatient(request.getDrugName(), patient.getAge(), weight, request.getBaseDose(), request.getOrderSetName());

    }

}