package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class JsonParser {
    private File file;
    private Map<String, String> depths;
    public JsonParser(File file) {
        this.file = file;
        depths = new HashMap<>();
    }
    public Map<String, String> getMapOfDepths() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(getJsonFile());
        parseDepths(array);
        return depths;
    }
    private String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
    private void parseDepths(JSONArray linesArray) {
        linesArray.forEach(depthObject -> {
            JSONObject depthJsonObject = (JSONObject) depthObject;
            depths.put(
                    (String) depthJsonObject.get("station_name"),
                    (String) depthJsonObject.get("depth")
            );
        });
    }
}
