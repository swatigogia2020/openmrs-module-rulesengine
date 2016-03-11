package org.openmrs.module.rulesengine.service;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;

public class PatientService {
    public Patient getPatientByUuid(String patientUuid) {
        Patient patient = Context.getPatientService().getPatientByUuid(patientUuid);
        if(null == patient){
            throw new APIException("Patient not found");
        }
        return patient;
    }
}
