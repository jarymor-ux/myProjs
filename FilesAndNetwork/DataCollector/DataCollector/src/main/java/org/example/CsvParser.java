package org.example;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class CsvParser {
    private File file;
    private Map<String, Set<String>> dates;


    public CsvParser(File file) {
        this.file = file;
        dates = new HashMap<>();
    }

    public Map<String, Set<String>> getMapOfDates() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> stationsAndDates = reader.readAll();
        stationsAndDates.remove(0);
        for (String[] stationAndDate : stationsAndDates) {
            if (dates.containsKey(stationAndDate[0])) {
                Set<String> extended = dates.get(stationAndDate[0]);
                extended.add(stationAndDate[1]);
                dates.put(stationAndDate[0], extended);
            } else {
                Set<String> newList = new HashSet<>();
                newList.add(stationAndDate[1]);
                dates.put(stationAndDate[0], newList);
            }
        }
        return dates;
    }
}