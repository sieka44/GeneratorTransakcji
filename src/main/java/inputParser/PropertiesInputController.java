package inputParser;

import dataGenerator.GeneratorValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Service
public class PropertiesInputController {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesInputController.class.getName());

    private static final String PROPERTIES_PATH = "/storage/generator.properties";
    private Properties properties = new Properties();
    private GeneratorValues generatorValues;

    public GeneratorValues parse() {
        LOGGER.debug("Using Properties");
        try {
            InputStream inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
            ZonedDateTime date = ZonedDateTime.now();
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            generatorValues = new GeneratorValues(
                    properties.getProperty("itemsFile") != null ? properties.getProperty("itemsFile") : "",
                    properties.getProperty("customersIds") != null ? properties.getProperty("customersIds") : "1:20",
                    properties.getProperty("dateRange") != null ? properties.getProperty("dateRange").replace("\"","") : date.withHour(0).withMinute(0).format(formatter) + ":" + date.withHour(23).withMinute(59).format(formatter),
                    properties.getProperty("itemsCount") != null ? properties.getProperty("itemsCount") : "1:5",
                    properties.getProperty("itemsQuantity") != null ? properties.getProperty("itemsQuantity") : "1:5",
                    properties.getProperty("eventsCount") != null ? properties.getProperty("eventsCount") : "100",
                    properties.getProperty("outDir") != null ? properties.getProperty("outDir") : "",
                    properties.getProperty("format") != null ? properties.getProperty("format") : "json"
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return generatorValues;
    }
}
