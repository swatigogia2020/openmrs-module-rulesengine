package org.openmrs.module.rulesengine.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.rulesengine.CIELDictionary;
import org.openmrs.module.rulesengine.util.RulesEngineProperties;

import java.util.Arrays;
import java.util.List;

public class ObservationService {

    public enum ConceptRepo {
        WEIGHT("Weight", "concept.weight.uuid", CIELDictionary.WEIGHT_UUID),
        HEIGHT("Height", "concept.height.uuid", CIELDictionary.HEIGHT_UUID);

        private String cName;
        private String cUuidKey;
        private String cielUuid;

        ConceptRepo(String cName, String cUuidKey, String cielUuid) {
            this.cName = cName;
            this.cUuidKey = cUuidKey;
            this.cielUuid = cielUuid;
        }
    }

    public static Double getLatestObsValueNumeric(Patient patient, ConceptRepo concept) throws Exception {
        Obs observation = getLatestObservation(patient, identifyConceptUuid(concept), concept.cName);
        if (null == observation) {
            throw new APIException(String.format("Observation for %s is not captured.", concept.cName));
        }
        return observation.getValueNumeric();
    }

    private static Obs getLatestObservation(Patient patient, String conceptUuid, String name) throws Exception {
        ObsService obsService = Context.getObsService();
        org.openmrs.Concept concept = Context.getConceptService().getConceptByUuid(conceptUuid);
        if (concept == null) {
            throw new APIException(String.format("Concept [%s] is not configured in rules.", name));
        }
        Encounter selectedEncounter = EncounterService.getLatestEncounterByPatient(patient);

        List<Obs> observations = obsService.getObservations(Arrays.asList(patient.getPerson()), Arrays.asList(selectedEncounter), Arrays.asList(concept),
            null, null, null, null, null, null, null, null, false);
        if (CollectionUtils.isEmpty(observations)) {
            return null;
        }
        return observations.get(0);
    }

    private static String identifyConceptUuid(ConceptRepo concept) {
        String conceptUuid = RulesEngineProperties.getProperty(concept.cUuidKey);
        return StringUtils.isBlank(conceptUuid) ? concept.cielUuid : conceptUuid;
    }
}
