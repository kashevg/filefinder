package filefinder;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger LOG = Logger.getLogger(Main.class.getName());
        if (args.length == 0) {
            LOG.warning("No parameter");
            System.exit(0);
        } else {
            Config.getInstance().readcfg();
            for (String str : Config.getInstance().getSTRING_ARRAY_FILE_EXCEPT())
                System.out.println(str);
            for (String str :  Config.getInstance().getSTRING_ARRAY_FILE_EXTENSIONS())
                System.out.println(str);
            FileSearcher fileSearcher = new FileSearcher(args[0], new WriteToConsole());
            fileSearcher.run();
        }
    }
}
