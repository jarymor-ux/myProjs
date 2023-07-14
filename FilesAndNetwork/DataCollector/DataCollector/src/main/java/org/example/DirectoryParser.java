package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class DirectoryParser {
    private final List<File> csvFiles = new ArrayList<>();
    private final List<File> jsonFiles = new ArrayList<>();
    public List<File> getCsvFiles() {return csvFiles;}
    public List<File> getJsonFiles() {return jsonFiles;}
    public DirectoryParser(String path){
        folderScan(new File(path));
    }
    private void folderScan(File dir) {
        for (File file : dir.listFiles()){
            if (file.isDirectory()) {
                if (!isEmpty(file)) {folderScan(file);}
            } else {
                if (isCsvFile(file)) {csvFiles.add(file);}
                else if (isJsonFile(file)){jsonFiles.add(file);}
            }
        }
    }
    private boolean isCsvFile(File file){
        return file.getName().endsWith(".csv");
    }
    private boolean isJsonFile(File file){
        return file.getName().endsWith(".json");
    }
    private boolean isEmpty(File dir){
        if (dir.listFiles() == null){ return true; }
        return dir.listFiles().length == 0;
    }
}