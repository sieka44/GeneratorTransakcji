import dataGenerator.Generator;
import inputParser.Cli;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneratorTransakcjiTest {

    private static final Logger LOGGER = LogManager.getLogger(GeneratorTransakcjiTest.class.getName());

    @Test
    public void jsonGeneratorTest(){
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime date = ZonedDateTime.now();
        Generator generator = new Generator("1:20",
                date.withHour(23).withMinute(59).format(formatter) + ":" + date.withHour(23).withMinute(59).format(formatter),
                "1:2",
                "1:5",
                "1",
                ""
                );
        generator.setItemsFile("items.csv");
        generator.generateEvents();
    }

}
