import org.json.simple.JSONObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneratorTransakcjiTest {

    @Test
    public void workingDirectory(){
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
    }

    @Test
    public void jsonGeneratorTest(){
        Generator generator = new Generator();
        generator.setCustomerIds("1:20");
        ZonedDateTime date = ZonedDateTime.now();
        generator.setDate("\"2018-03-08T00:00:00.000-0100\""+":"+"\"2018-03-08T23:59:59.999-0100\"");
        generator.setItemsFile("items.csv");
        generator.setItemsCount("1:2");
        generator.setItemsQuantity("1:5");
        generator.setEventsCount("1");
        generator.setOutDir("");
        generator.generateJson();
    }

}
