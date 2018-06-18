package inputParser;

import dataGenerator.Generator;
import dataGenerator.GeneratorValues;
import fileWriter.FileWriter;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Cli {
    private static final Logger LOGGER = LogManager.getLogger(Cli.class.getName());
    private final Options options = new Options();
    private GeneratorValues generatorValues;
    CommandLine cmd;

    public Cli() {
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
        options.addOption("outDir", true, "output dir");
        options.addOption("format", true, "output format");
        options.addOption("broker",true,"URL of broker");
        options.addOption("queue",true,"queue name");
        options.addOption("topic",true,"topic name");
        LOGGER.info("Options has been build");
    }

    public GeneratorValues parse(String[] args) throws ParseException {
        LOGGER.debug("using Cli");
        CommandLineParser parser = new BasicParser();
        cmd = parser.parse(options, args);
        if (!cmd.hasOption("customersIds")) LOGGER.warn("Seting default value for customersIds.");
        if (!cmd.hasOption("dateRange")) LOGGER.warn("Seting default value for dateRange.");
        if (!cmd.hasOption("itemsCount")) LOGGER.warn("Seting default value for itemsCount.");
        if (!cmd.hasOption("itemsQuantity")) LOGGER.warn("Seting default value for itemsQuantity.");
        if (!cmd.hasOption("eventsCount")) LOGGER.warn("Seting default value for eventsCount.");
        if (!cmd.hasOption("outDir")) LOGGER.warn("Seting default value for outDir.");
        if (!cmd.hasOption("format")) LOGGER.warn("Seting default value for format.");
        ZonedDateTime date = ZonedDateTime.now();
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        generatorValues = new GeneratorValues(
                cmd.getOptionValue("itemsFile", "items.csv"),
                cmd.getOptionValue("customersIds", "1:20"),
                cmd.getOptionValue("dateRange", date.withHour(0).withMinute(0).format(formatter) + ":" + date.withHour(23).withMinute(59).format(formatter)),
                cmd.getOptionValue("itemsCount", "1:5"),
                cmd.getOptionValue("itemsQuantity", "1:5"),
                cmd.getOptionValue("eventsCount", "100"),
                cmd.getOptionValue("outDir", ""),
                cmd.getOptionValue("format", "json"),
                cmd.getOptionValue("broker",""),
                cmd.getOptionValue("queue",""),
                cmd.getOptionValue("topic","")
        );
        return generatorValues;
    }



}

