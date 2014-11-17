package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by eugene on 17.11.2014.
 */
class FileSearcher {
    private ExecutorService pool;
    private String stringtofind;
    private FilenameFilter fileFilter;
    public FileSearcher(String strtexttofind){
        this.stringtofind = strtexttofind;
        fileFilter = new FilenameFilter() {
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
    }

    public  void run() {
        pool = Executors.newFixedThreadPool(3);
        for (File path: File.listRoots()) {
            getFileLists(path);
        }
        pool.shutdown();
    }

    private void getFileLists(File path){
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
                StringSearcher task = new StringSearcher(stringtofind, path.getAbsolutePath(), stringBuffer);
                pool.submit(task);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
