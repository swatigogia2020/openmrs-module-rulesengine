package org.openmrs.module.rulesengine.domain;

import org.bahmni.csv.CSVEntity;
import org.bahmni.csv.annotation.CSVHeader;
import org.springframework.stereotype.Component;

@Component
public class OrderSetDrugRow extends CSVEntity {

    @CSVHeader(name = "Name")
    private String name;

    @CSVHeader(name = "min_weight")
    private String minWeight;

    @CSVHeader(name = "max_weight")
    private String maxWeight;

    @CSVHeader(name = "min_age")
    private String minAge;

    @CSVHeader(name = "max_age")
    private String maxAge;

    @CSVHeader(name = "dosage")
    private String dosage;

    @CSVHeader(name = "rule")
    private String rule;

    public String getName() {
        return name;
    }

    public String getMinWeight() {
        return minWeight;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public String getMinAge() {
        return minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public String getDosage() {
        return dosage;
    }

    public String getRule() {
        return rule;
    }

    public OrderSetDrugRow() {
    }

    public OrderSetDrugRow(String name, int minAge, int maxAge, double minWeight, double maxWeight, int dosage, String rule) {
        this.name = name;
        this.minWeight = String.valueOf(minWeight);
        this.maxWeight = String.valueOf(maxWeight);
        this.minAge = String.valueOf(minAge);
        this.maxAge = String.valueOf(maxAge);
        this.dosage = String.valueOf(dosage);
        this.rule=rule;
    }

}
