package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import lombok.Getter;

public class Parser
{

    private Map<String, Page> pages = new HashMap<>();


    @Getter
    private String cachePath = "";


    @Getter
    private List<String> allowedDomains = null;


    @Getter
    private int maxThreads = 3;



    public Parser(List<String> allowedDomains, String cachePath, int maxThreads)
    {
        this.cachePath = cachePath;
        this.allowedDomains = allowedDomains;
        this.maxThreads = maxThreads;
    }



    public void run(String url)
    {

        Page page = new Page(url, null);
        boolean hasAdded = this.addPage(page);
        if (hasAdded)
        {
            PageAction action = new PageAction(this, page);
            ForkJoinPool pool = new ForkJoinPool(this.maxThreads);
            pool.invoke(action);
        }
    }



    public synchronized boolean addPage(Page page)
    {

        if (page.getDomain() == null)
        {
            return false;
        }
        if (page.getDomain().length() == 0)
        {
            return false;
        }


        if (page.getShortUrl() == null)
        {
            return false;
        }


        if (!allowedDomains.contains(page.getDomain()))
        {
            return false;
        }


        if (this.pages.containsKey(page.getShortUrl()))
        {
            return false;
        }


        this.pages.put(page.getShortUrl(), page);

        return true;
    }



    public Page getPageByUrl(String url)
    {
        return this.pages.get(url);
    }



    public List<Page> getPages()
    {
        ArrayList<Page> result = new ArrayList<>();

        for (Map.Entry<String, Page> entry : pages.entrySet())
        {
            result.add(entry.getValue());
        }

        return result;
    }



    public List<Page> getChildsPages(Page parentPage)
    {
        return pages.values()
                .stream()
                .filter(
                        (Page page) -> {
                            if (page.getParentPage() == null && parentPage == null)
                            {
                                return true;
                            }
                            if (page.getParentPage() != null && page.getParentPage().equals(parentPage))
                            {
                                return true;
                            }
                            return false;
                        }
                )
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList())
                ;
    }
}
