package org.openmrs.module.rulesengine.domain;

public class Dose {
    private double value;
    private DoseUnit doseUnit;

    public Dose(double value, DoseUnit doseUnit) {
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
}
