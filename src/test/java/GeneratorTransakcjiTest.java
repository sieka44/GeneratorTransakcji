import org.json.simple.JSONObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneratorTransakcjiTest {
    @Test
    public void test(){
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        //SimpleDateFormat parser =new  SimpleDateFormat("2018-03-08T00:00:00.000-0100");
        ZonedDateTime date = ZonedDateTime.parse("2018-03-08T00:00:00.000-0100",formatter);

//        Instant instant = Instant.parse("2018-03-08T00:00:00.000Z");
//        LocalDateTime date = LocalDateTime.from(instant);
        System.out.println(date);
    }

    @Test
    public void JSONTest(){
        JSONObject object = new JSONObject();
        object.put("yes","7");
        System.out.println(Double.parseDouble(object.get("yes").toString()));
    }
}
