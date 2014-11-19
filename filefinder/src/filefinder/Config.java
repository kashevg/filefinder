package filefinder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by eugene on 19.11.2014.
 */
public class Config {

    private int INT_THREAD_POOL_SIZE = 3;
    private String STRING_FILE_EXTENSIONS = ".txt";
    private String[] STRING_ARRAY_FILE_EXTENSIONS;
    private String STRING_FILE_EXCEPT = "/proc";
    private String[] STRING_ARRAY_FILE_EXCEPT;

    public String[] getSTRING_ARRAY_FILE_EXCEPT() {
        return STRING_ARRAY_FILE_EXCEPT;
    }

    public String[] getSTRING_ARRAY_FILE_EXTENSIONS() {
        return STRING_ARRAY_FILE_EXTENSIONS;
    }

    public int getINT_THREAD_POOL_SIZE() {
        return INT_THREAD_POOL_SIZE;
    }

    private static Config ourInstance = new Config();

    public static Config getInstance() {
        return ourInstance;
    }
    public void readcfg() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("./config/config.ini")));
            INT_THREAD_POOL_SIZE = Integer.valueOf(prop.getProperty("INT_THREAD_POOL_SIZE", "3"));
            STRING_FILE_EXCEPT = prop.getProperty("STRING_FILE_EXCEPT", "/proc");
            STRING_FILE_EXTENSIONS = prop.getProperty("STRING_FILE_EXTENSIONS", ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            STRING_ARRAY_FILE_EXCEPT = STRING_FILE_EXCEPT.split(";");
            STRING_ARRAY_FILE_EXTENSIONS = STRING_FILE_EXTENSIONS.split(";");
        }
    }

    private Config() {
    }
}
