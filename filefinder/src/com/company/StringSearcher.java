package com.company;

/**
 * Created by eugene on 16.11.2014.
 */
public class StringSearcher implements Runnable {

    String strToFind, fileName;
    StringBuffer stringBuffer;
    WriteResult writeResult;
    public StringSearcher(String strToFind, String fileName, StringBuffer stringBuffer, WriteResult writeResult) {
        this.strToFind = strToFind;
        this.fileName = fileName;
        this.stringBuffer = stringBuffer;
        this.writeResult = writeResult;
    }

    public void run() {
        int i = 0;
        do
        {
            i = stringBuffer.indexOf(strToFind, i);
            if (i != -1) {
                writeResult.write(fileName, i);
                i = i + strToFind.length();
            }
        } while (i>0);
    }
}
