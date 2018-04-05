package dataGenerator;

import inputParser.Cli;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            for (String s:args
                 ) {
                System.out.println(s);
            }
            new Cli().parse(args);
        } catch (ParseException e) {
            LOGGER.error("Parse error occurred - \"itemsFile\" must be defined",new ParseException("Cli parse Exception"));
            e.printStackTrace();
        }
    }
}
