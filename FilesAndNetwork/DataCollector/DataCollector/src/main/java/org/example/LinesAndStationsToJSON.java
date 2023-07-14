package org.example;

import org.example.Line;
import org.example.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LinesAndStationsToJSON {
    List<Line> lines;

    public LinesAndStationsToJSON(List<Line> lines) {
        this.lines = lines;
    }

    public void formJSONfile() {
        JSONObject general = new JSONObject();
        JSONObject stationsOfLines = new JSONObject();
        for (Line line : lines) {
            JSONArray list = new JSONArray();
            for (Station station : line.getStations()) {
                list.add(station.getName());
            }
            stationsOfLines.put(line.getName(), list);
        }
        general.put("stations", stationsOfLines);

        JSONArray lines = new JSONArray();
        for (Line line : this.lines) {
            JSONObject lineInfo = new JSONObject();
            lineInfo.put("number", line.getCode());
            lineInfo.put("name", line.getName());
            lines.add(lineInfo);
        }
        general.put("lines", lines);

        try (FileWriter file = new FileWriter("data/lines.json")) {
            file.write(general.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
