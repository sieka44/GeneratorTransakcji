import org.apache.commons.cli.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Cli {
    //private static final Logger logger = LogManager.getLogger(Cli.class.getName());
    private final Options options = new Options();
    private Generator generator;

    public Cli() {
        buildOptions();
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
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

    public void parse(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);
        ZonedDateTime date = ZonedDateTime.now();
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
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

