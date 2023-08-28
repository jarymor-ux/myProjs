import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OptimizedLoader {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        final PrintWriter[] writers = new PrintWriter[numThreads];
        for (int i = 0; i < numThreads; i++) {
            writers[i] = new PrintWriter("res/numbers_" + i + ".txt");
        }

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        for (int regionCode = 1; regionCode < 100; regionCode++) {
            final int finalRegionCode = regionCode;
            executorService.execute(() -> {
                int threadIndex = (int) Thread.currentThread().getId() % numThreads;
                PrintWriter writer = writers[threadIndex];
                StringBuilder builder = new StringBuilder();
                for (int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                builder.append(firstLetter)
                                        .append(padNumber(number, 3))
                                        .append(secondLetter)
                                        .append(thirdLetter)
                                        .append(padNumber(finalRegionCode, 2))
                                        .append("\n");
                            }
                        }
                    }
                }
                writer.write(builder.toString());
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (PrintWriter writer : writers) {
            writer.flush();
            writer.close();
        }

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();

        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }

        return numberStr;
    }
}
