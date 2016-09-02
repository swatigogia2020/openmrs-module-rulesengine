package org.openmrs.module.rulesengine.rule;

import org.bahmni.csv.CSVFile;
import org.openmrs.module.rulesengine.engine.RulesEngine;
import org.openmrs.module.rulesengine.engine.RulesEngineImpl;
import org.openmrs.module.rulesengine.util.CSVReader;
import org.bahmni.csv.exception.MigrationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.openmrs.Patient;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.domain.OrderSetDrugRow;
import org.openmrs.module.rulesengine.service.ObservationService;
import org.openmrs.module.rulesengine.service.PatientService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PatientService.class, ObservationService.class})
public class CSVDosageConverterImplTest {

    CSVBasedDosageRule csvBasedDosageRule;

    @Mock
    Patient patient;

    CSVReader<OrderSetDrugRow> csvReader;

    @Mock
    private RulesEngine rulesEngine;

    private String csvFileName = "testcsvfile";

    private String CSVBaseFolderPath = "/test";

    @Before
    public void setUp() throws Exception {
        csvReader = mock(CSVReader.class);
        csvBasedDosageRule = new CSVBasedDosageRule(csvReader,new RulesEngineImpl());
        mockStatic(PatientService.class);
        mockStatic(ObservationService.class);

        when(patient.getAge()).thenReturn(20);
        when(PatientService.getPatientByUuid(anyString())).thenReturn(patient);
        when(ObservationService.getLatestObsValueNumeric(any(Patient.class), any(ObservationService.ConceptRepo.class)))
                .thenReturn(50.0);

    }

    @Test(expected = MigrationException.class)
    public void shouldThrowExceptionWhenCSVFileIsNotAvailable() throws Exception {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        when(csvReader
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenThrow(new MigrationException("csv file not found"));
        DosageRequest request = new DosageRequest("test", "patientId", 10, "mg", "testorderset", "test");
        csvBasedDosageRule.calculateDose(request);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDrugIsNotFoundInCSV() throws Exception {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 200, 20.0, 60, 150,"block"));
        drugRows.add(new OrderSetDrugRow("drug2", 13, 200, 30.0, 35.9, 300,"block"));

        when(csvReader
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        DosageRequest request = new DosageRequest("drug10", "patientId", 10, "mg", "testorderset", "custom");
        csvBasedDosageRule.calculateDose(request);
    }

    @Test
    public void shouldReturnBlockedDoseWhenCSVRuleIsBlock() throws Exception {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 200, 20.0, 60, 150,"block"));
        drugRows.add(new OrderSetDrugRow("drug2", 13, 200, 30.0, 35.9, 300,"block"));

        when(csvReader
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        DosageRequest request = new DosageRequest("drug1", "patientId", 10, "mg", "testorderset", "custom");
        Dose newDose = csvBasedDosageRule.calculateDose(request);
        assertNotNull(newDose);
        assertEquals("drug1", newDose.getDrugName());
        assertEquals(newDose.getValue(), 150, 0);

    }

    @Test
    public void shouldReturnRuleBasedDoseWhenCSVRuleIsHavingRuleOtherThanBlock() throws Exception {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 200, 20.0, 60, 10,"mg/kg"));
        DosageRule dosageRule=mock(WeightBasedDosageRule.class);
        when(dosageRule.calculateDose(any(DosageRequest.class))).thenReturn(new Dose("drug1",500, Dose.DoseUnit.mg));
        when(rulesEngine.getRuleObject("mg/kg")).thenReturn(dosageRule);
        when(csvReader
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        DosageRequest request = new DosageRequest("drug1", "patientId", 10, "mg", "testorderset", "custom");
        Dose newDose = csvBasedDosageRule.calculateDose(request);
        assertNotNull(newDose);
        assertEquals("drug1", newDose.getDrugName());
        assertEquals(newDose.getValue(), 500, 0);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenBeanNotDefinedForTheRule() throws Exception {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 200, 20.0, 60, 10, "notdefined"));
        DosageRule dosageRule = mock(WeightBasedDosageRule.class);
        when(dosageRule.calculateDose(any(DosageRequest.class))).thenReturn(new Dose("drug1", 500, Dose.DoseUnit.mg));
        when(rulesEngine.getRuleObject("notdefined")).thenReturn(null);
        when(csvReader
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        DosageRequest request = new DosageRequest("drug1", "patientId", 10, "mg", "testorderset", "custom");
        csvBasedDosageRule.calculateDose(request);
    }

}