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
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime date = ZonedDateTime.now();
        System.out.println(date.format(formatter));
        generator.setDate(date.withHour(23).withMinute(59).format(formatter) + "\":\"" + date.withHour(23).withMinute(59).format(formatter)+"\"");
        generator.setItemsFile("items.csv");
        generator.setItemsCount("1:2");
        generator.setItemsQuantity("1:5");
        generator.setEventsCount("1");
        generator.setOutDir("");
        generator.generateJson();
    }

}
