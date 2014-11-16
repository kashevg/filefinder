package com.company;


import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        LinkedList<File> files = new LinkedList<File>();
        for (File path: File.listRoots()) {
            files.addAll(getFileLists(path));
        }
        for (File file : files) System.out.println(file);
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

    private static LinkedList<File> getFileLists(File path){
        LinkedList<File> files = new LinkedList<File>();
        files.clear();
        if (path.isDirectory()) {
            try {
                File[] list = path.listFiles(fileFilter);
                if (list != null){
                    for (File file : list) {
                        files.addAll(getFileLists(file));
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                //return files;
            }
        }
        else {
            files.add(path.getAbsoluteFile());
        }
        return files;
    }
}
