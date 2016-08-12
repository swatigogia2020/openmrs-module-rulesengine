package org.openmrs.module.rulesengine.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.csv.CSVFile;
import org.openmrs.module.rulesengine.util.CSVReader;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.domain.OrderSetDrugRow;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.openmrs.module.rulesengine.util.RulesEngineProperties;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("customrule")
public class CSVBasedDosageRule implements DosageRule {

    private CSVReader<OrderSetDrugRow> csvReader;

    private static final String CSVFileRelativePath = "rulesengine"+File.separator+"drugorderrules";

    @Autowired
    private ApplicationContext applicationContext;


    public CSVBasedDosageRule() {
        csvReader = new CSVReader<>();
    }

    private static Log log = LogFactory.getLog(CSVBasedDosageRule.class);

    public CSVBasedDosageRule(CSVReader<OrderSetDrugRow> csvReader, ApplicationContext applicationContext) {
        this.csvReader = csvReader;
        this.applicationContext=applicationContext;
    }

    @Override
    public Dose calculateDose(DosageRequest request) throws Exception {
        String csvFilePath = getCSVFilePath(CSVFileRelativePath);
        log.info("Using CSV File path:" + csvFilePath);
        CSVFile<OrderSetDrugRow> csvFile = new CSVFile<>(csvFilePath, request.getOrderSetName() + ".csv");
        List<OrderSetDrugRow> orderSetDrugRowList = csvReader.readCsvFileToMemory(csvFile, OrderSetDrugRow.class);
        if (orderSetDrugRowList != null && orderSetDrugRowList.size() > 0) {

            Patient patient = PatientService.getPatientByUuid(request.getPatientUuid());

            Double weight = ObservationService.getLatestObsValueNumeric(patient, ObservationService.ConceptRepo.WEIGHT);

            for (OrderSetDrugRow orderSetDrugRow : orderSetDrugRowList) {
                if (orderSetDrugRow.getName().equals(request.getDrugName())) {
                    if (Integer.parseInt(orderSetDrugRow.getMinAge()) <= patient.getAge() && patient.getAge() < Integer.parseInt(orderSetDrugRow.getMaxAge())
                            && Double.parseDouble(orderSetDrugRow.getMinWeight()) <= weight && weight < Double.parseDouble(orderSetDrugRow.getMaxWeight())
                            ) {
                        if (orderSetDrugRow.getRule().equals("block")) {
                            double roundedUpDoseValue = Double.parseDouble(orderSetDrugRow.getDosage());
                            return new Dose(orderSetDrugRow.getName(), roundedUpDoseValue, Dose.DoseUnit.mg);
                        }
                        DosageRule rule = applicationContext.getBean(orderSetDrugRow.getRule(), DosageRule.class);
                        request.setBaseDose(Double.parseDouble(orderSetDrugRow.getDosage()));
                        return rule.calculateDose(request);
                    }
                }
            }
        }

        log.warn("Returning original dosage without conversion");
        throw new Exception("Drug definition not found in CSV file for:"+request.getDrugName());
    }
    private String getCSVFilePath(String relativePath) {
        return OpenmrsUtil.getApplicationDataDirectory()+File.separator+
                "bahmni_config"+File.separator+"openmrs"+File.separator+relativePath;
    }

}