package src;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveTask;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkExecutor extends RecursiveTask<String> {

    private String url;
    private static CopyOnWriteArrayList<String> allLinks = new CopyOnWriteArrayList<>();


    public LinkExecutor(String url) {
        this.url = url.trim();
    }

    @Override
    protected String compute() {
        String tabulate = StringUtils.repeat("\t",
                url.lastIndexOf("/") != url.length() - 1 ? StringUtils.countMatches(url, "/") - 2
                        : StringUtils.countMatches(url, "/") - 3);
        StringBuffer stringBuffer = new StringBuffer(tabulate + url + "\n");
        List<LinkExecutor> allTasks = new CopyOnWriteArrayList<>();
        Document document;
        Elements elements;
        try {
            Thread.sleep(150);
            document = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0").get();
            elements = document.select("a[href]");
            for (Element element : elements) {
                String attributeUrl = element.absUrl("href");
                if (!attributeUrl.isEmpty() && attributeUrl.startsWith(url) && !allLinks.contains(attributeUrl) && !attributeUrl
                        .contains("#")) {
                    LinkExecutor linkExecutor = new LinkExecutor(attributeUrl);
                    linkExecutor.fork();
                    allTasks.add(linkExecutor);
                    allLinks.add(attributeUrl);
                }
            }
        } catch (InterruptedException | IOException e) {

            Thread.currentThread().interrupt();
        }

        Collections.sort(allTasks, Comparator.comparing(o -> o.url));
        for (LinkExecutor link : allTasks) {
            stringBuffer.append(link.join());
        }
        return stringBuffer.toString();
    }
}