import java.io.File;

public class Main {

    public static void main(String[] args) {
        String srcFolder = "C:\\pic";
        String dstFolder = "C:\\dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();
        int newWidth = 300;
        File[] files = srcDir.listFiles();

        int onePart = files.length / 14;

        File[] files1 = new File[onePart];
        System.arraycopy(files, 0, files1,0,files1.length);
        ImageResizer resizer1 = new ImageResizer(files1,newWidth,dstFolder,start);
        new Thread(resizer1).start();

        File[] files2 = new File[files.length - onePart * 2];
        System.arraycopy(files, onePart*2, files2,0,files2.length);
        ImageResizer resizer2 = new ImageResizer(files2,newWidth,dstFolder,start);
        new Thread(resizer2).start();

        File[] files3 = new File[files.length - onePart*3];
        System.arraycopy(files, onePart*3, files3,0,files3.length);
        ImageResizer resizer3 = new ImageResizer(files3,newWidth,dstFolder,start);
        new Thread(resizer3).start();

        File[] files4 = new File[files.length - onePart*4];
        System.arraycopy(files, onePart*4, files4,0,files4.length);
        ImageResizer resizer4 = new ImageResizer(files4,newWidth,dstFolder,start);
        new Thread(resizer4).start();

        File[] files5 = new File[files.length - onePart*5];
        System.arraycopy(files, onePart*5, files5,0,files5.length);
        ImageResizer resizer5 = new ImageResizer(files5,newWidth,dstFolder,start);
        new Thread(resizer5).start();

        File[] files6 = new File[files.length - onePart*6];
        System.arraycopy(files, onePart*6, files6,0,files6.length);
        ImageResizer resizer6 = new ImageResizer(files6,newWidth,dstFolder,start);
        new Thread(resizer6).start();

        File[] files7 = new File[files.length - onePart*7];
        System.arraycopy(files, onePart*7, files7,0,files7.length);
        ImageResizer resizer7 = new ImageResizer(files7,newWidth,dstFolder,start);
        new Thread(resizer7).start();

        File[] files8 = new File[files.length - onePart*8];
        System.arraycopy(files, onePart*8, files8,0,files8.length);
        ImageResizer resizer8 = new ImageResizer(files8,newWidth,dstFolder,start);
        new Thread(resizer8).start();

        File[] files9 = new File[files.length - onePart*9];
        System.arraycopy(files, onePart*9, files9,0,files9.length);
        ImageResizer resizer9 = new ImageResizer(files9,newWidth,dstFolder,start);
        new Thread(resizer9).start();

        File[] files10 = new File[files.length - onePart*10];
        System.arraycopy(files, onePart*10, files10,0,files10.length);
        ImageResizer resizer10 = new ImageResizer(files10,newWidth,dstFolder,start);
        new Thread(resizer10).start();

        File[] files11 = new File[files.length - onePart*11];
        System.arraycopy(files, onePart*11, files11,0,files11.length);
        ImageResizer resizer11 = new ImageResizer(files11,newWidth,dstFolder,start);
        new Thread(resizer11).start();

        File[] files12 = new File[files.length - onePart*12];
        System.arraycopy(files, onePart*12, files12,0,files12.length);
        ImageResizer resizer12 = new ImageResizer(files12,newWidth,dstFolder,start);
        new Thread(resizer12).start();

        File[] files13 = new File[files.length - onePart*13];
        System.arraycopy(files, onePart*13, files13,0,files13.length);
        ImageResizer resizer13 = new ImageResizer(files13,newWidth,dstFolder,start);
        new Thread(resizer13).start();

        File[] files14 = new File[files.length - onePart*14];
        System.arraycopy(files, onePart*14, files14,0,files14.length);
        ImageResizer resizer14 = new ImageResizer(files14,newWidth,dstFolder,start);
        new Thread(resizer14).start();


    }
}
