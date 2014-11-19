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
            FileSearcher fileSearcher = new FileSearcher(args[0], new WriteToConsole());
            fileSearcher.run();
        }
    }
}
