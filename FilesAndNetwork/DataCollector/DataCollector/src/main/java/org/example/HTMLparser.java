package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class HTMLparser {
    private List<Station> stations = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    public HTMLparser(String path) throws Exception {

        Document document = Jsoup.parse(Paths.get(path).toFile());

        Elements linesElements = document.
                select("span.js-metro-line.t-metrostation-list-header.t-icon-metroln");
        for (Element lineElement : linesElements) {
            Line line = new Line(lineElement.attr("data-line"), lineElement.text());
            lines.add(line);
            Element nextEl = lineElement.parent().nextElementSibling();
            for (Element child : nextEl.getElementsByAttributeValue("class", "single-station")) {
                Element stationName = child.getElementsByAttributeValue("class", "name").first();
                Station station = new Station(stationName.text(), line,
                        stationName.nextElementSibling() != null);
                line.addStation(station);
                stations.add(station);
            }
        }


    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Station> getStations() {
        return stations;
    }
}
