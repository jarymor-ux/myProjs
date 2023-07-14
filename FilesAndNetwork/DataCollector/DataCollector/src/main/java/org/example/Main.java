package org.example;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String pageCodePath = "data/index.html";
        HTMLparser htmLparser = new HTMLparser(pageCodePath);
        List<Line> lines = htmLparser.getLines();
        List<Station> stations = htmLparser.getStations();



        String directoryPath = "data/data";
        DirectoryParser directoryScanner = new DirectoryParser(directoryPath);
        List<File> jsounFiles = directoryScanner.getJsonFiles();
        List<File> csvFiles = directoryScanner.getCsvFiles();


        Map<String, String> depths = new HashMap<>();
        for (File file : jsounFiles) {
            JsonParser jsonParser = new JsonParser(file);
            depths.putAll(jsonParser.getMapOfDepths());
        }


        Map<String, Set<String>> dates = new HashMap<>();
        for (File file : csvFiles) {
            CsvParser csvParser = new CsvParser(file);
            Map<String, Set<String>> DatesFromFile = csvParser.getMapOfDates();
            DatesFromFile.keySet().forEach(key -> {
                if (dates.containsKey(key)) {
                    Set<String> lokalDates = dates.get(key);
                    lokalDates.addAll(DatesFromFile.get(key));
                    dates.put(key, lokalDates);
                } else {
                    dates.put(key, DatesFromFile.get(key));
                }
            });
        }

        LinesAndStationsToJSON linesAndStationsToJSON = new LinesAndStationsToJSON(lines);
        linesAndStationsToJSON.formJSONfile();

        StationsInfoJSON stationsInfoJSON = new StationsInfoJSON(stations, depths, dates);
        stationsInfoJSON.formJSONfile();
    }
}