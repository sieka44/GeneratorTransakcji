import org.apache.commons.cli.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Cli {
    private static final Logger LOGGER = LogManager.getLogger(Cli.class.getName());
    private final Options options = new Options();
    private Generator generator;

    Cli() {
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
        LOGGER.info("Options has been build");
    }

    public void parse(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);
        ZonedDateTime date = ZonedDateTime.now();
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if(!cmd.hasOption("customersIds"))LOGGER.warn("Set default value for customersIds.");
        if(!cmd.hasOption("dateRange"))LOGGER.warn("Set default value for dateRange.");
        if(!cmd.hasOption("itemsCount"))LOGGER.warn("Set default value for itemsCount.");
        if(!cmd.hasOption("itemsQuantity"))LOGGER.warn("Set default value for itemsQuantity.");
        if(!cmd.hasOption("eventsCount"))LOGGER.warn("Set default value for eventsCount.");
        if(!cmd.hasOption("outDir"))LOGGER.warn("Set default value for outDir.");
        generator = new Generator(
                cmd.getOptionValue("customersIds", "1:20"),
                cmd.getOptionValue("dateRange", date.withHour(0).withMinute(0).format(formatter) + ":" + date.withHour(23).withMinute(59).format(formatter)),
                cmd.getOptionValue("itemsCount", "1:5"),
                cmd.getOptionValue("itemsQuantity", "1:5"),
                cmd.getOptionValue("eventsCount", "100"),
                cmd.getOptionValue("outDir", "")
        );
        generator.setItemsFile(cmd.getOptionValue("itemsFile", "items.csv"));
        generator.generateEvents();
    }

}

