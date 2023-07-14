package org.example;

import org.example.Station;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line> {
    private String code;
    private String name;
    private List<Station> stations;

    public Line(String code, String name) {
        this.code = code;
        this.name = name;
        stations = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public int compareTo(Line line){
        return (code.compareTo(line.getCode()));
    }



    @Override
    public boolean equals(Object obj) {
        return compareTo((Line) obj) == 0;
    }

    @Override
    public String toString() {return name;}
}