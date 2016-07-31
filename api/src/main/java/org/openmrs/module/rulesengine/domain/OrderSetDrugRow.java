package org.openmrs.module.rulesengine.domain;

import org.bahmni.csv.CSVEntity;
import org.bahmni.csv.annotation.CSVHeader;
import org.springframework.stereotype.Component;

@Component
public class OrderSetDrugRow extends CSVEntity {

    @CSVHeader(name = "Name")
    private String name;

    @CSVHeader(name = "min_weight")
    private double minWeight;

    @CSVHeader(name = "max_weight")
    private double maxWeight;

    @CSVHeader(name = "min_age")
    private int minAge;

    @CSVHeader(name = "max_age")
    private int maxAge;

    @CSVHeader(name = "dosage")
    private int dosage;

    public String getName() {
        return name;
    }

    public double getMinWeight() {
        return minWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getDosage() {
        return dosage;
    }

    public OrderSetDrugRow() {
    }

    public OrderSetDrugRow(String name, int minAge, int maxAge, double minWeight, double maxWeight, int dosage) {
        this.name = name;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.dosage = dosage;
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
