import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Line;
import core.Station;


public class RouteCalculatorTest
{
    StationIndex stationIndex;

    @BeforeEach
    protected void setUp()
    {
        stationIndex = new StationIndex();

        // Создаем линии со станциями
        Line line1 = new Line(1, "line_1");
        line1.addStation(new Station("line_1_station_1", line1));
        line1.addStation(new Station("line_1_station_2", line1));
        line1.addStation(new Station("line_1_station_3", line1));
        line1.addStation(new Station("line_1_station_4", line1));

        // Линия 2
        Line line2 = new Line(2, "line_2");
        line2.addStation(new Station("line_2_station_1", line2));
        line2.addStation(new Station("line_2_station_2", line2));
        line2.addStation(new Station("line_2_station_3", line2));
        line2.addStation(new Station("line_2_station_4", line2));

        // Линия 3
        Line line3 = new Line(2, "line_3");
        line3.addStation(new Station("line_3_station_1", line3));
        line3.addStation(new Station("line_3_station_2", line3));
        line3.addStation(new Station("line_3_station_3", line3));
        line3.addStation(new Station("line_3_station_4", line3));

        // На эту ветку попасть будет нельзя
        Line line4 = new Line(2, "line_4");
        line4.addStation(new Station("line_4_station_1", line4));
        line4.addStation(new Station("line_4_station_2", line4));
        line4.addStation(new Station("line_4_station_3", line4));
        line4.addStation(new Station("line_4_station_4", line4));

        // Добавляем линии
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);

        // Добавляем станции на линиях в stationIndex
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);

        for (Line line : lines)
        {
            for (Station station : line.getStations())
            {
                stationIndex.addStation(station);
            }
        }

        // Создаем пересадки
        ArrayList<Station> connection1 = new ArrayList<>();
        connection1.add( stationIndex.getStation("line_1_station_2") );
        connection1.add( stationIndex.getStation("line_2_station_2") );
        stationIndex.addConnection(connection1);

        ArrayList<Station> connection3 = new ArrayList<>();
        connection3.add( stationIndex.getStation("line_2_station_4") );
        connection3.add( stationIndex.getStation("line_3_station_4") );
        stationIndex.addConnection(connection3);
    }


    @Test
    public void testFromSeminar()
    {
        List<Station> route = new ArrayList<>();

        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");

        route.add(new Station("Петровская", line1));
        route.add(new Station("Арбузная", line1));
        route.add(new Station("Морковная", line2));
        route.add(new Station("Яблочная", line2));

        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5f;
        assertEquals(actual, expected);
    }


    @Test
    public void testCalculateDurationOneLine()
    {
        List<Station> route = new ArrayList<>();

        route.add(stationIndex.getStation("line_1_station_1"));
        route.add(stationIndex.getStation("line_1_station_2"));
        route.add(stationIndex.getStation("line_1_station_3"));
        route.add(stationIndex.getStation("line_1_station_4"));

        double actual = RouteCalculator.calculateDuration(route);
        double expected = 7.5f;
        assertEquals(actual, expected);
    }


    @Test
    public void testCalculateDurationWithConnection()
    {
        List<Station> route = new ArrayList<>();

        route.add(stationIndex.getStation("line_1_station_1"));
        route.add(stationIndex.getStation("line_1_station_2"));
        route.add(stationIndex.getStation("line_2_station_2"));
        route.add(stationIndex.getStation("line_2_station_3"));

        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5f;
        assertEquals(actual, expected);
    }


    @Test
    public void testEmptyRoute()
    {
        Station from = stationIndex.getStation("line_1_station_1");
        Station to = stationIndex.getStation("line_4_station_4");

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> route = routeCalculator.getShortestRoute(from, to);

        assertTrue(route.size() == 0, "Empty route list");
    }


    @Test
    public void testRouteOnTheLine()
    {
        Station from = stationIndex.getStation("line_1_station_1");
        Station to = stationIndex.getStation("line_1_station_4");

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> route = routeCalculator.getShortestRoute(from, to);

        String actual = getRouteNames(route);
        String expected = "line_1_station_1\n" +
                "line_1_station_2\n" +
                "line_1_station_3\n" +
                "line_1_station_4"
                ;

        assertEquals(actual, expected, "RouteOnTheLine");
    }


    @Test
    public void testRouteWithOneConnection()
    {
        Station from = stationIndex.getStation("line_1_station_1");
        Station to = stationIndex.getStation("line_2_station_3");

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> route = routeCalculator.getShortestRoute(from, to);

        String actual = getRouteNames(route);
        String expected = "line_1_station_1\n" +
                "line_1_station_2\n" +
                "line_2_station_2\n" +
                "line_2_station_3"
                ;

        assertEquals(actual, expected, "RouteWithOneConnection");
    }


    @Test
    public void testRouteWithTwoConnections()
    {
        Station from = stationIndex.getStation("line_1_station_1");
        Station to = stationIndex.getStation("line_3_station_1");

        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        List<Station> route = routeCalculator.getShortestRoute(from, to);

        String actual = getRouteNames(route);
        String expected = "line_1_station_1\n" +
                "line_1_station_2\n" +
                "line_2_station_2\n" +
                "line_2_station_3\n" +
                "line_2_station_4\n" +
                "line_3_station_4\n" +
                "line_3_station_3\n" +
                "line_3_station_2\n" +
                "line_3_station_1"
                ;

        assertEquals(actual, expected, "RouteWithTwoConnections");
    }


    public String getRouteNames(List<Station> route)
    {
        List<String> s = route.stream()
                .map((item) -> item.getName())
                .collect(Collectors.toList())
                ;
        return String.join("\n", s);
    }

}
