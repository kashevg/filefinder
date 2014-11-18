package filefinder;

/**
 * Created by eugene on 18.11.2014.
 */
public class WriteToConsole implements WriteResult {
    @Override
    public void write(String fileName, int pos) {
        System.out.println("File: " + fileName + " at pos: " + pos);
    }
}
