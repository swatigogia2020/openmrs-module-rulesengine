package org.openmrs.module.rulesengine.engine;

import org.bahmni.csv.CSVEntity;
import org.bahmni.csv.CSVFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apalani on 7/31/16.
 */
@Component
public class CSVFileReader<T extends CSVEntity> {

    public List<T> readCsvFileToMemory(CSVFile csvFile, Class<T> entityClass) {
        try {
            T csvEntity;
            List<T> csvRowsFromFile = new ArrayList<>();
            csvFile.openForRead();
            while ((csvEntity = (T) csvFile.readEntity(entityClass)) != null) {
                csvRowsFromFile.add(csvEntity);
            }
            return csvRowsFromFile;
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
