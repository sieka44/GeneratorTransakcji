package inputParser;

import dataGenerator.Generator;
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
    @Autowired
    GenericApplicationContext appContext;

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
        LOGGER.info("Options has been build");
    }

    public void parse(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        cmd = parser.parse(options, args);
        if (!cmd.hasOption("customersIds")) LOGGER.warn("Seting default value for customersIds.");
        if (!cmd.hasOption("dateRange")) LOGGER.warn("Seting default value for dateRange.");
        if (!cmd.hasOption("itemsCount")) LOGGER.warn("Seting default value for itemsCount.");
        if (!cmd.hasOption("itemsQuantity")) LOGGER.warn("Seting default value for itemsQuantity.");
        if (!cmd.hasOption("eventsCount")) LOGGER.warn("Seting default value for eventsCount.");
        if (!cmd.hasOption("outDir")) LOGGER.warn("Seting default value for outDir.");
        if (!cmd.hasOption("format")) LOGGER.warn("Seting default value for format.");
        initSpring();
        Generator generator = (Generator) (appContext.getBean("generator"));
        generator.generateEvents();
    }
//    private static Cli initSpring(){
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.registerBean(Cli.class);
//        applicationContext.scan("fileWriter");
//        applicationContext.refresh();
//        GenericApplicationContext ctx = applicationContext;
//        return (Cli) ctx.getBean("cli");
//    }

    private void initSpring() {
        ZonedDateTime date = ZonedDateTime.now();
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.registerBean(FileInputController.class, () -> new FileInputController(cmd.getOptionValue("itemsFile", "items.csv")));
        applicationContext.scan("fileWriter");
        appContext = applicationContext;
        appContext.refresh();
        appContext.registerBean(Generator.class, () -> new Generator(
                cmd.getOptionValue("customersIds", "1:20"),
                cmd.getOptionValue("dateRange", date.withHour(0).withMinute(0).format(formatter) + ":" + date.withHour(23).withMinute(59).format(formatter)),
                cmd.getOptionValue("itemsCount", "1:5"),
                cmd.getOptionValue("itemsQuantity", "1:5"),
                cmd.getOptionValue("eventsCount", "100"),
                cmd.getOptionValue("outDir", ""),
                (FileWriter) applicationContext.getBean(cmd.getOptionValue("format", "json"))
        ));
    }

}

