package org.openmrs.module.rulesengine.domain;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class DosageRequest {

    private String patientUuid;
    private String drugName;
    private double baseDose;
    private String doseUnit;
    private String orderSetName;
    private String dosingRule;

    private DosageRequest() {
    }

    public DosageRequest(String drugName, String patientUuid, double baseDose, String doseUnit, String orderSetName, String dosingRule) {
        this.patientUuid = patientUuid;
        this.drugName = drugName;
        this.baseDose = baseDose;
        this.doseUnit = doseUnit;
        this.orderSetName = orderSetName;
        this.dosingRule=dosingRule;
    }

    public DosageRequest(String jsonRequest) throws IOException {
        DosageRequest req=deserialize(jsonRequest);
        this.patientUuid = req.getPatientUuid();
        this.drugName = req.getDrugName();
        this.baseDose = req.getBaseDose();
        this.doseUnit = req.getDoseUnit();
        this.dosingRule=req.getDosingRule();
        this.orderSetName = req.getOrderSetName();
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

    private DosageRequest deserialize(String jsonRequest) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonRequest, DosageRequest.class);
    }
}
