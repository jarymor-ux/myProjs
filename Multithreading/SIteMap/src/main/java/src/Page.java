package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import lombok.Getter;
import lombok.Setter;


public class Page implements Comparable<Page>
{

    public static final int MIN_TIME = 25;
    public static final int MAX_TIME = 50;



    public static final List<String> disallowedGetParameters = new ArrayList<>()
    {{
        add("utm_source");
        add("utm_medium");
        add("utm_content");
        add("utm_campaign");
        add("utm_term");
        add("ysclid");
        add("yclid");
        add("gclid");
        add("media");
    }};

    @Getter
    private String fullUrl = "";

    @Getter
    private String shortUrl = "";

    @Getter
    private Page parentPage = null;

    @Getter
    private String domain = "";

    @Getter
    private URL url = null;

    @Getter
    @Setter
    private boolean isValid = true;



    public Page(String fullUrl, Page parentPage)
    {
        this.fullUrl = fullUrl;
        this.parentPage = parentPage;


        try
        {
            this.url = new URL(fullUrl);

            String domain = this.url.getHost();
            String query = this.url.getQuery();
            String path = this.url.getPath();

            if (domain == null)
            {
                domain = "";
            }
            if (query == null)
            {
                query = "";
            }
            if (path == null)
            {
                path = "";
            }

            this.domain = this.url.getHost();
            this.shortUrl = buildShortUrl(this.url);


            this.shortUrl = removeSharp(this.shortUrl);
        }
        catch (MalformedURLException e)
        {
            this.shortUrl = null;
        }
    }



    public String getDomainWithProtocol()
    {
        if (this.url != null)
        {
            return this.url.getProtocol() + "://" + this.getDomain();
        }
        return "https://" + this.getDomain();
    }



    public String getNewHref(String href)
    {
        if (href.length() == 0)
        {
            return fullUrl;
        }

        href = removeSharp(href);
        href = filterGetParameters(href);
        href = trimUrl(href);

        try
        {
            URL hrefUrl = new URL(href);


            if (
                    !hrefUrl.getProtocol().equals("http") &&
                            !hrefUrl.getProtocol().equals("https")
            )
            {
                return null;
            }


            if (!this.getDomain().equals(hrefUrl.getHost()))
            {
                return href;
            }

            href = getUrlWithoutDomain(hrefUrl);
        }
        catch (MalformedURLException e)
        {
        }

        href = removeTwoSlash(href);


        int pos1 = href.indexOf(":");
        if (pos1 > -1)
        {
            return null;
        }



        if (href.length() > 0 && href.charAt(0) != '/')
        {
            String result = this.fullUrl;


            int pos2 = result.lastIndexOf("/");
            if (pos2 == -1)
            {
                return null;
            }

            result = result.substring(0, pos2);
            return result + "/" + href;
        }


        if (href.length() == 0)
        {
            href = "/";
        }

        return this.getDomainWithProtocol() + href;
    }



    public static String removeSharp(String url)
    {
        int posSharp = url.indexOf("#");
        if (posSharp >= 0)
        {
            url = url.substring(0, posSharp);
        }
        return url;
    }



    public static String removeTwoSlash(String url)
    {
        url = url.replaceAll("/+", "/");
        return url;
    }



    public static String buildShortUrl(URL url)
    {
        StringBuilder s = new StringBuilder();

        s.append(url.getHost());

        if (url.getPath().length() == 0)
        {
            s.append("/");
        }
        else
        {
            s.append(url.getPath());
        }

        if (url.getQuery() != null && url.getQuery().length() > 0)
        {
            s.append("?" + url.getQuery());
        }

        String result = s.toString();

        if (result.length() == 0)
        {
            result = "/";
        }

        return result;
    }



    public static String getUrlWithoutDomain(URL url)
    {
        StringBuilder s = new StringBuilder();

        s.append(url.getPath());

        if (url.getQuery() != null && url.getQuery().length() > 0)
        {
            s.append("?" + url.getQuery());
        }

        if (url.getRef() != null && url.getRef().length() > 0)
        {
            s.append("#" + url.getRef());
        }

        String result = s.toString();

        if (result.length() == 0)
        {
            result = "/";
        }

        return result;
    }



    public static String filterGetParameters(String href)
    {
        int posQuery = href.indexOf("?");
        if (posQuery == -1)
        {
            return href;
        }

        String path = href.substring(0, posQuery);
        String query = href.substring(posQuery + 1);


        String[] params = query.split("&");
        ArrayList<String >parameters = new ArrayList<>();
        for (String param : params)
        {

            String[] keyValue = param.split("=");


            if (keyValue.length == 2)
            {
                String key = keyValue[0];
                String value = keyValue[1];

                if (disallowedGetParameters.indexOf(key) == -1)
                {
                    parameters.add(key + "=" + value);
                }
            }
        }


        query = String.join("&", parameters);

        return path + "?" + query;
    }



    public static String trimUrl(String href)
    {
        href = href.trim();

        while (href.length() > 3 && href.substring(href.length() - 3).equals("%20"))
        {
            href = href.substring(0, href.length()-3);
        }



        return href;
    }



    public static int getLevelUrl(String url)
    {
        try
        {
            URL urlObj = new URL(url);
            url = getUrlWithoutDomain(urlObj);
        }
        catch (MalformedURLException e)
        {
        }

        int posQuery = url.indexOf("?");
        if (posQuery != -1)
        {
            url = url.substring(0, posQuery);
        }

        String[] arr = url.split("/");
        int level = 0;

        for (int i=0; i<arr.length; i++)
        {
            String s = arr[i];
            if (s.isEmpty())
            {
                continue;
            }

            level += 1;
        }

        return level;
    }



    public static int compareLinks(String url1, String url2)
    {
        url1 = url1.replaceAll("/+", "/");
        url2 = url2.replaceAll("/+", "/");

        String[] arr1 = url1.split("/");
        String[] arr2 = url2.split("/");

        int sz = Math.min(arr1.length, arr2.length);
        for (int i=0; i<sz; i++)
        {
            int c = arr1[i].compareTo(arr2[i]);
            if (c < 0) return -1;
            if (c > 0) return 1;
        }

        if (arr1.length > sz) return 1;
        if (arr2.length > sz) return -1;

        return 0;
    }



    public String getCacheFilePath(String cachePath)
    {
        return buildCacheFilePath(this.shortUrl, cachePath);
    }



    public static String buildCacheFilePath(String shortUrl, String cachePath)
    {

        String domain = "";
        int posDomain = shortUrl.indexOf("/");
        if (posDomain == -1)
        {
            domain = shortUrl;
            shortUrl = "";
        }
        else
        {
            domain = shortUrl.substring(0, posDomain);
            shortUrl = shortUrl.substring(posDomain);
        }


        int posSharp = shortUrl.indexOf("#");
        if (posSharp >= 0)
        {
            shortUrl = shortUrl.substring(0, posSharp);
        }


        int pos = shortUrl.indexOf("?");

        String path = (pos >= 0) ? shortUrl.substring(0, pos) : shortUrl;
        String query = (pos >= 0) ? shortUrl.substring(pos) : "";


        if (path.length() == 0 || path.equals("/"))
        {
            path = "index.html";
        }


        if (path.endsWith("/"))
        {
            path = path.substring(0, path.length() - 1);
            path += ".html";
        }


        String fileExtension = "";
        int dotIndex = path.lastIndexOf(".");
        if (dotIndex >= 0)
        {
            fileExtension = path.substring(dotIndex + 1);
            path = path.substring(0, dotIndex);
        }


        if (fileExtension.length() == 0)
        {
            fileExtension = "html";
        }


        query = query.replace('?', '_').replace('=', '_').replace('&', '_');


        shortUrl = query.length() > 0 ? path + query : path;


        shortUrl += "." + fileExtension;

        return Paths.get(cachePath, domain, shortUrl).toString();
    }



    public Document parse(String cachePath)
    {
        String cacheFilePath = this.getCacheFilePath(cachePath);

        File file = new File(cacheFilePath);
        if (!file.exists())
        {

            boolean success = downloadPage(this.fullUrl, cacheFilePath);
            if (!success)
            {
                return null;
            }
        }


        Document doc = parseFile(cacheFilePath);
        return doc;
    }



    public static Document parseFile(String filePath)
    {
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                return null;
            }


            Document doc = Jsoup.parse(file, "UTF-8");
            return doc;
        }
        catch (IOException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }

        return null;
    }



    public static boolean downloadPage(String url, String filePath)
    {
        Random random = new Random();

        System.out.println("Thread-" +
                Thread.currentThread().getId() + ". Download page: " +
                url
        );

        try
        {

            Thread.sleep( random.nextLong(MIN_TIME, MAX_TIME) );
        }
        catch (InterruptedException e)
        {
            return false;
        }


        try
        {
            Path path = Path.of(filePath);
            Path parentPath = path.getParent();


            if (!parentPath.toFile().exists())
            {
                Files.createDirectories(parentPath);
            }

            try
            {
                URL contentUrl = new URL(url);
                InputStream inputStream = contentUrl.openStream();

                Files.copy(
                        inputStream, path,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
            catch (FileNotFoundException e)
            {

                try
                {
                    FileWriter file = new FileWriter(filePath);
                    file.write("404");
                    file.flush();
                    file.close();
                }
                catch (IOException e2)
                {
                }
            }
        }
        catch (IOException e)
        {
            System.out.println
                    (
                            "Ошибка при скачивании или сохранении содержимого: " + e.getMessage()
                    );
            return false;
        }

        return true;
    }


    public int compareTo(Page obj)
    {
        return getShortUrl().compareTo(obj.getShortUrl());
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof Page))
        {
            return false;
        }

        Page page = (Page) o;

        return page.getShortUrl().equals(getShortUrl());
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(shortUrl);
    }
}
