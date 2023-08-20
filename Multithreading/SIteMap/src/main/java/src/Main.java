package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main
{
    static Parser parser = null;

    public static void main(String[] args)
    {
        parseSkillbox();
        saveLinks();
    }



    public static void parseSkillbox()
    {
        ArrayList<String> allowedDomains = new ArrayList<>();
        allowedDomains.add("skillbox.ru");

        String cachePath = "./data";
        int maxThreads = 3;

        parser = new Parser(allowedDomains, cachePath, maxThreads);
        parser.run("https://skillbox.ru/");
    }



    public static void saveLinks()
    {
        System.out.println("Сохранить список ссылок в файл");
        try
        {
            FileWriter file;
            file = new FileWriter("C:\\dst\\skillbox.txt");

            HashSet<String> urls = new HashSet<>();
            List<Page> pages = parser.getPages();
            pages.sort((Page page1, Page page2) -> {
                return Page.compareLinks(page1.getFullUrl(), page2.getFullUrl());
            });

            for (int i=0; i<pages.size(); i++)
            {
                Page page = pages.get(i);
                String url = page.getFullUrl();


                if (url.length() == 0)
                {
                    continue;
                }


                if (!page.isValid())
                {
                    continue;
                }


                int pos = page.getFullUrl().indexOf("?");
                if (pos >= 0)
                {
                    url = url.substring(0, pos);
                }


                if (url.charAt(url.length() - 1) != '/')
                {
                    url += "/";
                }


                if (urls.contains(url))
                {
                    continue;
                }

                urls.add(url);

                int level = Page.getLevelUrl(url);
                String s = "\t".repeat(level) + page.getFullUrl();
                file.write(s + "\n");
            }

            file.flush();
            file.close();
        }
        catch (IOException e)
        {
        }
    }

}
