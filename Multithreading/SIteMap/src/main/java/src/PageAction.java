package src;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageAction extends RecursiveAction
{
    private Parser parser = null;
    private Page page = null;


    public PageAction(Parser parser, Page page)
    {
        this.parser = parser;
        this.page = page;
    }


    @Override
    protected void compute()
    {

        System.out.println("Thread-" +
                Thread.currentThread().getId() + " - Parse page: " +
                this.page.getFullUrl()
        );
        Document doc = this.page.parse(this.parser.getCachePath());

        if (doc == null)
        {
            this.page.setValid(false);
            return;
        }


        ArrayList<PageAction> tasks = new ArrayList<>();


        Elements lineElements = doc.select("a");
        for (Element lineElement : lineElements)
        {
            String href = lineElement.attr("href").trim();


            String newUrl = this.page.getNewHref(href);


            if (newUrl == null)
            {
                continue;
            }


            Page page = new Page(newUrl, this.page);
            boolean hasAdded = this.parser.addPage(page);


            if (hasAdded)
            {
                PageAction action = new PageAction(parser, page);
                tasks.add(action);
            }
        }

        invokeAll(tasks);
    }

}
