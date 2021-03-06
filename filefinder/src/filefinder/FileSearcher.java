package filefinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by eugene on 17.11.2014.
 */
public class FileSearcher {
    private ExecutorService pool;
    private String stringToFind;
    private FilenameFilter fileFilter;
    private WriteResult writeResult;
    private Logger LOG;

    private Config getConfig() {
        return Config.getInstance();
    }
    public FileSearcher(String strTextToFind, WriteResult writeResult){
        this.stringToFind = strTextToFind;
        this.writeResult = writeResult;
        LOG = Logger.getLogger(FileSearcher.class.getName());
        fileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File file = new File(dir, name);
                if (file.isDirectory()) {
                    return true;
                }
                try {
                    int posDot = name.lastIndexOf('.');
                    if (posDot < 0)
                        return false;
                    name = name.substring(posDot);
                    for(String ext : getConfig().getArrFileExtensions()) {
                        if (name.equals(ext))
                            return true;
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        };
    }

    public  void run() {
        getConfig().readCfg();
        pool = Executors.newFixedThreadPool(getConfig().getThreadPoolSize());
        for (File path: File.listRoots()) {
            getFileLists(path);
        }

        pool.shutdown();
    }

    private void getFileLists(File path){
        for (String exceptFile : getConfig().getArrFileExcept() ) {
            if (path.getAbsolutePath().equals(exceptFile) )
                return;
        }
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
                StringSearcher task = new StringSearcher(stringToFind, path.getAbsolutePath(), stringBuffer, writeResult);
                pool.submit(task);
            } catch (Exception e) {
                LOG.warning(e.getMessage());
            }
        }
    }
}
