import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.ZonedDateTime;

public class Cli {
    private static final Logger logger = LogManager.getLogger(Cli.class.getName());
    private Options options = new Options();
    private Generator generator;

    public Cli() {
        generator = new Generator();
        buildOptions();
    }

    private void buildOptions() {
        options.addOption("customerIds", true, "generated Ids of customers");
        options.addOption("dateRange", true, "range of generated date");
        Option option = new Option("itemsFile", true, "name of the csv file contains list of products");
        option.setRequired(true);
        options.addOption(option);
        options.addOption("itemsCount", true, "range of generated products");
        options.addOption("itemsQuantity", true, "range of generated product amount");
        options.addOption("eventsCount", true, "range of generated product amount");
        options.addOption("outDir", true, "range of generated product amount");
    }

    public void parse(String[] args) {
        CommandLineParser parser = new BasicParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            generator.setCustomerIds(cmd.getOptionValue("customersIds", "1:20"));
            ZonedDateTime date = ZonedDateTime.now();
            generator.setDate(cmd.getOptionValue("dateRange", date.withHour(0).withMinute(0).toString() + ":" + date.withHour(23).withMinute(59).toString()));
            generator.setItemsFile(cmd.getOptionValue("itemsFile", "items.csv"));
            generator.setItemsCount(cmd.getOptionValue("itemsCount", "1:5"));
            generator.setItemsQuantity(cmd.getOptionValue("itemsQuantity", "1:5"));
            generator.setEventsCount(cmd.getOptionValue("eventsCount", "100"));
            generator.setOutDir(cmd.getOptionValue("outDir", ""));
            generator.generateJson();
        } catch (ParseException e) {
            //LOGGER
            e.printStackTrace();
        }
    }

}

