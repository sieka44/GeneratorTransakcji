import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneratorTest {

    Generator generator;

    @Before
    public void init() {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime date = ZonedDateTime.now();
        generator = new Generator("1:20",
                date.withHour(23).withMinute(59).format(formatter) + ":" + date.withHour(23).withMinute(59).format(formatter),
                "1:2",
                "1:5",
                "1",
                ""
        );
    }

    @Test
    public void wrongData() {
        Generator generator = new Generator("", "", "", "", "", "");
        FileController fileController = Mockito.mock(FileController.class);
        Mockito.when(fileController.getSumAndReset()).thenReturn(0.0);
        generator.setFileController(fileController);
        JSONObject json = generator.generateOneJson();
    }
}
