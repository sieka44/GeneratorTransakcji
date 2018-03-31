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
        Cli cli = new Cli();
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
    /*
    -customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" -itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 10 -outDir ./output

     */

}
