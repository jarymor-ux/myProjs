package org.example;

public class Station implements Comparable<Station>
{
    private Line line;
    private String name;
    private boolean hasConnection;

    public Station(String name, Line line, boolean hasConnection)
    {
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;
    }

    public Line getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }
    public boolean doesHaveConnection()
    {
        return hasConnection;
    }

    @Override
    public int compareTo(Station station)
    {
        int lineComparison = line.compareTo(station.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Station) obj) == 0;
    }

    @Override
    public String toString()
    {
        return name;
    }
}