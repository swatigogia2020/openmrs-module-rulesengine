package org.openmrs.module.rulesengine.domain;

public class DosageRequest {
    private String patientUuid;
    private String drugName;
    private double baseDose;
    private String doseUnit;
    private String orderSetName;
    private String dosingRule;

    public DosageRequest() {
    }

    public DosageRequest(String drugName, String patientUuid, double baseDose, String doseUnit, String orderSetName) {
        this.patientUuid = patientUuid;
        this.drugName = drugName;
        this.baseDose = baseDose;
        this.doseUnit = doseUnit;
        this.orderSetName = orderSetName;
    }

    public String getPatientUuid() {
        return patientUuid;
    }

    public void setPatientUuid(String patientUuid) {
        this.patientUuid = patientUuid;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public double getBaseDose() {
        return baseDose;
    }

    public void setBaseDose(double baseDose) {
        this.baseDose = baseDose;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public String getOrderSetName() {
        return orderSetName;
    }

    public void setOrderSetName(String orderSetName) {
        this.orderSetName = orderSetName;
    }

    public String getDosingRule() { return dosingRule; }

    public void setDosingRule(String dosingRule) { this.dosingRule = dosingRule; }
}
