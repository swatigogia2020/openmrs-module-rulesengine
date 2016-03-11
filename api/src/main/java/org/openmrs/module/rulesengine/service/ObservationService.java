package org.openmrs.module.rulesengine.service;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.*;
import org.openmrs.api.APIException;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.rulesengine.CIELDictionary;

import java.util.Arrays;
import java.util.List;

public class ObservationService {

    public static Double getLatestWeight(Patient patient) throws Exception {
        Obs observation = getLatestObservation(patient, CIELDictionary.WEIGHT_UUID);
        if(null == observation){
            throw new APIException("Weight is not available");
        }
        return observation.getValueNumeric();
    }

    public static Double getLatestHeight(Patient patient) throws Exception {
        Obs observation = getLatestObservation(patient, CIELDictionary.HEIGHT_UUID);
        if(null == observation){
            throw new APIException("Height is not available");
        }
        return observation.getValueNumeric();
    }

    private static Obs getLatestObservation(Patient patient, String conceptUuid) throws Exception {
        ObsService obsService = Context.getObsService();
        Concept concept = Context.getConceptService().getConceptByUuid(conceptUuid);
        Encounter selectedEncounter = EncounterService.getLatestEncounterByPatient(patient);

        List<Obs> observations = obsService.getObservations(Arrays.asList(patient.getPerson()), Arrays.asList(selectedEncounter), Arrays.asList(concept),
            null, null, null, null, null, null, null, null, false);
        if(CollectionUtils.isEmpty(observations)){
            return null;
        }
        return observations.get(0);
    }

}
