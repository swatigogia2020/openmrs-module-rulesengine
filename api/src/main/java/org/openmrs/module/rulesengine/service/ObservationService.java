package org.openmrs.module.rulesengine.service;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.rulesengine.CIELDictionary;

import java.util.Arrays;
import java.util.List;

public class ObservationService {
    public Double getLatestWeight(Person person, Encounter selectedEncounter) throws Exception {
        try {
            return getLatestObservation(person, selectedEncounter, CIELDictionary.WEIGHT_UUID);
        } catch (ObservationNotFoundException e) {
            throw new APIException("Weight is not available");
        }
    }

    public Double getLatestHeight(Person person, Encounter selectedEncounter) throws Exception {
        try {
            return getLatestObservation(person, selectedEncounter, CIELDictionary.HEIGHT_UUID);
        } catch (ObservationNotFoundException e) {
            throw new APIException("Height is not available");
        }
    }

    public Double getLatestObservation(Person person, Encounter selectedEncounter, String conceptUuid) throws Exception {
        ObsService obsService = Context.getObsService();
        Concept concept = Context.getConceptService().getConceptByUuid(conceptUuid);

        List<Obs> obss = obsService.getObservations(Arrays.asList(person), Arrays.asList(selectedEncounter), Arrays.asList(concept),
            null, null, null, null, null, null, null, null, false);
        if(CollectionUtils.isEmpty(obss)){
            throw new ObservationNotFoundException();
        }
        return obss.get(0).getValueNumeric();
    }

    public class ObservationNotFoundException extends Exception{
        public ObservationNotFoundException() {
            super("No Observation found.");
        }
    }
}
