package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static ExecutorService pool;
    private static String sermoning;
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        } else {
            sermoning = args[0];
        }
        pool = Executors.newFixedThreadPool(3);
        for (File path: File.listRoots()) {
            getFileLists(path);
        }
        pool.shutdown();
    }

    static FilenameFilter fileFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            File file = new File(dir, name);
            if (file.isDirectory()) {
                return true;
            }
            try {
                name = name.substring(name.lastIndexOf("."));
                switch(name) {
                    case ".txt": return true;
                    default: return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    };

    private static void getFileLists(File path){
        if (path.isDirectory()) {
            try {
                File[] list = path.listFiles(fileFilter);
                if (list != null)
                    for (File file : list)
                        getFileLists(file);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line).append("\n");
                }
                StringSearcher task = new StringSearcher(sermoning, path.getAbsolutePath(), stringBuffer);
                pool.submit(task);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
