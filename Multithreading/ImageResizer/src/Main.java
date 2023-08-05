import java.io.File;

public class Main {

    public static void main(String[] args) {
        String srcFolder = "C:\\pic";
        String dstFolder = "C:\\dst";

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        int newWidth = 300;
        long start = System.currentTimeMillis();

        int processorsCount = Runtime.getRuntime().availableProcessors();

        int onePart = files.length / processorsCount;

        int position = 0;

        for (int i=processorsCount - 1; i>=0; i--)
        {
            if (i == 0)
            {
                onePart = files.length - position;
            }

            File[] files2 = new File[onePart];
            System.arraycopy(files, position, files2, 0, onePart);

            ImageResizer resizer = new ImageResizer(files2, newWidth, dstFolder, start);
            new Thread(resizer).start();

            position += onePart;
        }

    }
}
