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

    public OrderSetDrugRow() {
    }

    public OrderSetDrugRow(String name, int minAge, int maxAge, double minWeight, double maxWeight, int dosage) {
        this.name = name;
        this.minWeight = String.valueOf(minWeight);
        this.maxWeight = String.valueOf(maxWeight);
        this.minAge = String.valueOf(minAge);
        this.maxAge = String.valueOf(maxAge);
        this.dosage = String.valueOf(dosage);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        } else {
            OrderSetDrugRow drugRow = (OrderSetDrugRow) object;
            if (this.name == drugRow.getName()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 7 * hash + this.name.hashCode();
        return hash;
    }
}
