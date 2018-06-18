package Launcher;

import controler.MqController;
import dataGenerator.Generator;
import dataGenerator.GeneratorValues;
import fileWriter.FileWriter;
import inputParser.Cli;
import inputParser.FileInputController;
import inputParser.PropertiesInputController;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static Cli cli = new Cli();
    private static GeneratorValues generatorValues;
    private static PropertiesInputController propertiesController = new PropertiesInputController();
    @Autowired
    Generator generator;

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                LOGGER.info("Generator will use Cli.");
                generatorValues = cli.parse(args);
            } else {
                LOGGER.info("Generator will use properties file.");
                generatorValues = propertiesController.parse();
            }
            Generator generator = initSpring();
            generator.generateTransactions();
        } catch (ParseException e) {
            LOGGER.error("Parse error occurred - \"itemsFile\" must be defined", new ParseException("Cli parse Exception"));
            e.printStackTrace();
        }
    }

    private static Generator initSpring() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.registerBean(FileInputController.class, () -> new FileInputController(generatorValues.getItemsFile()));
        applicationContext.scan("fileWriter");
        applicationContext.refresh();
        applicationContext.registerBean(Generator.class, () -> new Generator(
                generatorValues.getCustomersIds(),
                generatorValues.getDateRange(),
                generatorValues.getItemsCount(),
                generatorValues.getItemsQuantity(),
                generatorValues.getEventsCount(),
                generatorValues.getOutDir(),
                (FileWriter) applicationContext.getBean(generatorValues.getFormat()),
                new MqController(generatorValues.getBroker(),generatorValues.getQueue(),generatorValues.getTopic())
        ));

        return (Generator) applicationContext.getBean("generator");
    }

}
