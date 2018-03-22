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
