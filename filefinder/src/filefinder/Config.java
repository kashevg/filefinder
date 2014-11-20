package filefinder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by eugene on 19.11.2014.
 */
public class Config {

    private int threadPoolSize = 3;
    private String fileExtensions = ".txt";
    private String[] arrFileExtensions;
    private String fileExcept = "/proc;/sys";
    private String[] arrFileExcept;

    public String[] getArrFileExcept() {
        return arrFileExcept;
    }

    public String[] getArrFileExtensions() {
        return arrFileExtensions;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    private static Config ourInstance = new Config();

    public static Config getInstance() {
        return ourInstance;
    }
    public void readCfg() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("./config/config.ini")));
            threadPoolSize = Integer.valueOf(prop.getProperty("INT_THREAD_POOL_SIZE", "3"));
            fileExcept = prop.getProperty("STRING_FILE_EXCEPT", "/proc");
            fileExtensions = prop.getProperty("STRING_FILE_EXTENSIONS", ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            arrFileExcept = fileExcept.split(";");
            arrFileExtensions = fileExtensions.split(";");
        }
    }

    private Config() {
    }
}
