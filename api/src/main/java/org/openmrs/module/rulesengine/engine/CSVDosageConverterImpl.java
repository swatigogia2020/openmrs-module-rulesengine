package org.openmrs.module.rulesengine.engine;

import org.bahmni.csv.CSVFile;
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

    @Autowired
    private CSVFileReader<OrderSetDrugRow> csvFileReader;

    private String csvBaseFilePath = "/opt/bahmni-web/etc/";

    public CSVDosageConverterImpl() {
        csvBaseFilePath = csvBaseFilePath + "bahmni_config" +
                File.separator + "openmrs" +
                File.separator + "drugOrderRules";
    }

    @Override
    public Dose convertDosageByPatient(String drugName, int age, Double weight, double baseDose, String orderSetName) {
        CSVFile<OrderSetDrugRow> csvFile = new CSVFile<>(csvBaseFilePath, orderSetName + ".csv");
        List<OrderSetDrugRow> orderSetDrugRowList = csvFileReader.readCsvFileToMemory(csvFile, OrderSetDrugRow.class);
        if (orderSetDrugRowList == null || orderSetDrugRowList.size() == 0)
            return null;
        for (OrderSetDrugRow orderSetDrugRow : orderSetDrugRowList) {
            if (orderSetDrugRow.getName().equals(drugName)) {
                if (orderSetDrugRow.getMinAge() <= age && age < orderSetDrugRow.getMaxAge()
                        && orderSetDrugRow.getMinWeight() <= weight && weight < orderSetDrugRow.getMaxWeight()
                        ) {
                    double roundedUpDoseValue = orderSetDrugRow.getDosage();
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