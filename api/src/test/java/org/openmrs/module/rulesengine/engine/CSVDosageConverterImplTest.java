package org.openmrs.module.rulesengine.engine;

import org.bahmni.csv.CSVFile;
import org.bahmni.csv.MultiStageMigrator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.module.rulesengine.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CSVDosageConverterImplTest {

    private DosageConverter dosageConverter;

    MultiStageMigrator<OrderSetDrugRow> multiStageMigrator ;

    private String csvFileName = "testcsvfile";

    @Before
    public void setUp() throws Exception {
        multiStageMigrator = mock(MultiStageMigrator.class);
        dosageConverter = new CSVDosageConverterImpl(multiStageMigrator);
    }

    @Test
    public void shouldReturnNullWhenCSVFileIsNotAvailableOrEmpty() {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        when(multiStageMigrator
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        Dose newDose = dosageConverter.convertDosageByPatient("drug1", 21, 21.0, 10, csvFileName);
        assertNull(newDose);
    }

    @Test
    public void shouldReturnDoseWhenMatchedByDrugNameAndAgeWeightRange() {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 200, 20.0, 24.9, 150));
        drugRows.add(new OrderSetDrugRow("drug2", 13, 200, 30.0, 35.9, 300));

        when(multiStageMigrator
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        Dose newDose = dosageConverter.convertDosageByPatient("drug1", 21, 23.0, 10, csvFileName);
        assertNotNull(newDose);
        assertEquals("drug1", newDose.getDrugName());
        assertEquals(newDose.getValue(), 150, 0);

    }


    @Test
    public void shouldReturnNullWhenNoMatchFoundByDrugName() {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 20, 80, 20, 200, 150));
        drugRows.add(new OrderSetDrugRow("drug2", 30, 90, 20, 200, 250));

        when(multiStageMigrator
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        Dose newDose = dosageConverter.convertDosageByPatient("drug3", 21, 21.0, 10, csvFileName);
        assertNull(newDose);
    }


    @Test
    public void shouldReturnNullWhenNameMatchAndAgeRangeNotMatch() {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 50, 20, 24.9, 150));
        drugRows.add(new OrderSetDrugRow("drug2", 13, 200, 30, 35.9, 300));

        when(multiStageMigrator
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        Dose newDose = dosageConverter.convertDosageByPatient("drug1", 55, 25.0, 10, csvFileName);
        assertNull(newDose);
    }

    @Test
    public void shouldReturnNullWhenNameMatchAndWeightRangeNotMatch() {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 13, 50, 20, 24.9, 150));
        drugRows.add(new OrderSetDrugRow("drug2", 13, 200, 30, 35.9, 300));

        when(multiStageMigrator
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        Dose newDose = dosageConverter.convertDosageByPatient("drug1", 25, 70.0, 10, csvFileName);
        assertNull(newDose);
    }

    @Test
    public void shouldReturnWeightBasedDosageCalculationWhenCSVDosageIsMinusOne() {
        List<OrderSetDrugRow> drugRows = new ArrayList<>();
        drugRows.add(new OrderSetDrugRow("drug1", 0, 12, 0, 5, -1));
        drugRows.add(new OrderSetDrugRow("drug2", 13, 200, 30, 35.9, 300));

        when(multiStageMigrator
                .readCsvFileToMemory(any(CSVFile.class), Matchers.<Class<OrderSetDrugRow>>any()))
                .thenReturn(drugRows);
        Dose newDose = dosageConverter.convertDosageByPatient("drug1", 2, 4.0, 10, csvFileName);
        assertNotNull(newDose);
        assertEquals(4 * 10, newDose.getValue(), 0);
    }

}