package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static String url;
    private static String fileName;
    private static final String DST_FOLDER = "src/main/resources/sitemap/";
    private static final String FILE_EXTENSION = "txt";
    private static int numberOfCores = Runtime.getRuntime().availableProcessors();
    private static int numberOfThreads;
    private static long startOfTime;


    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите адрес сайта для сканирования: =>> Согласно примера: https://lenta.ru");
            url = reader.readLine();
            fileName = new URL(url).getHost().replace(".", "_");

            System.out.println("Введите количество создаваемых потоков: \n\t=>> 0 - согласно количеству ядер процессора");
            numberOfThreads = Integer.parseInt(reader.readLine());

            System.out.println("Начато сканирование сайта: " + LocalTime.now());
            startOfTime = System.currentTimeMillis();

            LinkExecutor linkExecutor = new LinkExecutor(url);
            String siteMap = new ForkJoinPool(numberOfThreads == 0 ? numberOfCores : numberOfThreads).invoke(linkExecutor);

            System.out.println("Закончено сканирование сайта: " + LocalTime.now());
            long periodOfTimeInSec = (System.currentTimeMillis() - startOfTime) / 1000;

            System.out.println("Время сканирования составило: " + periodOfTimeInSec + " сек.");

            writeToFile(siteMap);

        } catch (IOException e) {

        } catch (IllegalArgumentException e) {

            System.err.println("Превышено допустимое количество, введите другое значение!");
        }
    }

    private static void writeToFile(String siteMap) {
        System.out.println("Производится запись данных в файл ==> ==> ==>");
        if (!Files.exists(Paths.get(DST_FOLDER))) {
            new File(DST_FOLDER).mkdir();
        }
        String filePath = DST_FOLDER.concat(fileName).concat(".").concat(FILE_EXTENSION);
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(siteMap);
            writer.flush();
        } catch (FileNotFoundException e) {

        }
        System.out.println("Файл записан:");
        long periodOfTimeInSec = (System.currentTimeMillis() - startOfTime) / 1000;
        System.out.println(file.getAbsolutePath());

    }
}
