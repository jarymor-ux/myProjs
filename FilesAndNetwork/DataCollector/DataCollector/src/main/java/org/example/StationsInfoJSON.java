package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StationsInfoJSON {
    List<Station> stations;
    Map<String, String> depths;
    Map<String, Set<String>> dates;

    public StationsInfoJSON(List<Station> stations, Map<String, String> depths, Map<String, Set<String>> dates) {
        this.stations = stations;
        this.depths = depths;
        this.dates = dates;
    }

    public void formJSONfile() {
        JSONObject general = new JSONObject();
        JSONArray stations = new JSONArray();
        for (Station station : this.stations){
            String stName = station.getName();
            String date;
            JSONObject stationObject = new JSONObject();
            if (this.dates.containsKey(stName)){
                ArrayList<String> dates = new ArrayList<>(this.dates.get(stName));
                date = dates.get(0);
                if (dates.size() > 1){
                    dates.remove(0);
                    Set<String> rewrited = new HashSet<>(dates);
                    this.dates.put(stName, rewrited);
                }
            } else {date = "no date";}

            stationObject.put("name", stName);
            stationObject.put("line", station.getLine().getName());
            stationObject.put("date", date);
            stationObject.put("depth", depths.get(stName) == null? "unknown" : depths.get(stName));
            stationObject.put("hasConnection", station.doesHaveConnection());
            stations.add(stationObject);
        }
        general.put("stations", stations);

        try (FileWriter file = new FileWriter("data/stationsInfo.json")) {
            file.write(general.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}