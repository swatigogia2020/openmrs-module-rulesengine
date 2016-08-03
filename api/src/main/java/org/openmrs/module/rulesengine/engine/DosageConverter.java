package org.openmrs.module.rulesengine.engine;

import org.openmrs.module.rulesengine.domain.Dose;

public interface DosageConverter {
    Dose convertDosageByPatient(String drug, int age, double weight, double baseDose, String orderSetName);
}