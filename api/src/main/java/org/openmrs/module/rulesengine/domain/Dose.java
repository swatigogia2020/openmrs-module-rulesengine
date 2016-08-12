package org.openmrs.module.rulesengine.domain;

public class Dose {
    private double value;
    private DoseUnit doseUnit;
    private String drugName;

    public Dose(String drugName, double value, DoseUnit doseUnit) {
        this.drugName=drugName;
        this.value = value;
        this.doseUnit = doseUnit;
    }

    public double getValue() {
        return value;
    }

    public DoseUnit getDoseUnit() {
        return doseUnit;
    }

    public enum DoseUnit {
        mg
    }

    public String getDrugName() {
        return drugName;
    }
}
