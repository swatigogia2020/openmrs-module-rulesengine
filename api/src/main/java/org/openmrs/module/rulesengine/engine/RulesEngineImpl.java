package org.openmrs.module.rulesengine.engine;

import org.bahmni.csv.MultiStageMigrator;
import org.bahmni.module.admin.csv.models.OrderSetDrugRow;
import org.openmrs.module.rulesengine.domain.Dose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by apalani on 7/31/16.
 */
@Component
public class RulesEngineImpl implements RulesEngine {

    @Autowired
    MultiStageMigrator<OrderSetDrugRow> multiStageMigrator;

    @Override
    public Dose calculateDose(String patientUUId, String drugName, String baseUnit, Double doseUnit, String orderSetName) throws Exception {
        return null;
    }

}
