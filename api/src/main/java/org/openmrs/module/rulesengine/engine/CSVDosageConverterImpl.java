package org.openmrs.module.rulesengine.engine;

import org.bahmni.csv.CSVFile;
import org.bahmni.csv.MultiStageMigrator;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.domain.OrderSetDrugRow;
import org.openmrs.module.rulesengine.util.BahmniMath;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class CSVDosageConverterImpl implements DosageConverter {

    private MultiStageMigrator<OrderSetDrugRow> multiStageMigrator;

    private String csvBaseFilePath = "/opt/bahmni-web/etc/";

    public CSVDosageConverterImpl() {
        csvBaseFilePath = csvBaseFilePath + "bahmni_config" +
                File.separator + "openmrs" +
                File.separator + "drugOrderRules";
        multiStageMigrator=new MultiStageMigrator<>();
    }

    public CSVDosageConverterImpl(MultiStageMigrator<OrderSetDrugRow> multiStageMigrator) {
        this.multiStageMigrator=multiStageMigrator;
    }

    @Override
    public Dose convertDosageByPatient(String drugName, int age, double weight, double baseDose, String orderSetName) {
        CSVFile<OrderSetDrugRow> csvFile = new CSVFile<>(csvBaseFilePath, orderSetName + ".csv");
        List<OrderSetDrugRow> orderSetDrugRowList = multiStageMigrator.readCsvFileToMemory(csvFile, OrderSetDrugRow.class);
        if (orderSetDrugRowList == null || orderSetDrugRowList.size() == 0)
            return null;
        for (OrderSetDrugRow orderSetDrugRow : orderSetDrugRowList) {
            if (orderSetDrugRow.getName().equals(drugName)) {
                if (Integer.parseInt(orderSetDrugRow.getMinAge()) <= age && age < Integer.parseInt(orderSetDrugRow.getMaxAge())
                        && Double.parseDouble(orderSetDrugRow.getMinWeight()) <= weight && weight < Double.parseDouble(orderSetDrugRow.getMaxWeight())
                        ) {
                    double roundedUpDoseValue =  Double.parseDouble(orderSetDrugRow.getDosage());
                    if (roundedUpDoseValue == -1) {
                        roundedUpDoseValue = BahmniMath.getTwoDigitRoundUpValue(baseDose * weight);
                    }
                    return new Dose(orderSetDrugRow.getName(), roundedUpDoseValue, Dose.DoseUnit.mg);
                }
            }
        }
        return null;
    }
}