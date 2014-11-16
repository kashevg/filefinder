package com.company;

/**
 * Created by eugene on 16.11.2014.
 */
public class StringSearcher implements Runnable {

    String sermoning, filename;
    StringBuffer stringBuffer;
    public StringSearcher(String sermoning, String filename, StringBuffer stringBuffer) {
        this.sermoning = sermoning;
        this.filename = filename;
        this.stringBuffer = stringBuffer;
    }

    public void run() {
        int i = 0;
        do
        {
            i = stringBuffer.indexOf(sermoning, i);
            if (i != -1) {
                System.out.println("File: " + filename + " at pos " + i);
                i = i + sermoning.length();
            }
        } while (i>0);
    }
}
