import dataGenerator.Generator;
import fileWriter.FileJsonWriter;
import inputParser.FileInputController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class GeneratorTest {

    @Mock
    FileInputController fileInputController;

    @InjectMocks
    Generator generator;

    @Test(expected = DateTimeParseException.class)
    public void wrongDateFormatTest() {
        generator = new Generator("", LocalDateTime.now() + ":" + LocalDateTime.now().plusDays(1), "", "", "-19", "", new FileJsonWriter());
        MockitoAnnotations.initMocks(this);
        Mockito.when(fileInputController.getSumAndReset()).thenReturn(BigDecimal.ZERO);
        generator.generateOneJson();
    }


    @Test
    public void correctDataTest() {
        int min = 1;
        int max = 5;
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime startDate = ZonedDateTime.now().withFixedOffsetZone();
        ZonedDateTime endDate = startDate.plusDays(1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "objName");
        jsonObject.put("quantity", "objNumber");
        jsonObject.put("price", "objPrice");
        Mockito.when(fileInputController.getSumAndReset()).thenReturn(BigDecimal.ZERO);
        Mockito.when(fileInputController.getRandomObject(any())).thenReturn(jsonObject);
        generator = new Generator(min + ":" + max, startDate.format(formatter) + ":" + endDate.format(formatter), min + ":" + max, "", "", "", new FileJsonWriter());
        MockitoAnnotations.initMocks(this);
        JSONObject json = generator.generateOneJson();
        int customerID = Integer.parseInt(json.get("customer_id").toString());
        ZonedDateTime dateTime = ZonedDateTime.parse(json.get("timestamp").toString());
        int itemsCount = ((JSONArray) json.get("items")).size();

        Assert.assertTrue(min <= customerID && customerID <= max);
        Assert.assertTrue(dateTime.isAfter(startDate) && dateTime.isBefore(endDate));
        Assert.assertTrue(min <= itemsCount && itemsCount <= max);
    }

    @Test
    public void generateEventsTest() {
        int eventCount = 10;
        FileJsonWriter jsonWriter = Mockito.mock(FileJsonWriter.class);
        generator = new Generator("", "", "", "", Integer.toString(eventCount), "", jsonWriter);
        MockitoAnnotations.initMocks(this);
        Generator uut = Mockito.spy(generator);
        Mockito.when(fileInputController.getRandomObject(any())).thenReturn(new JSONObject());
        uut.generateEvents();
        Mockito.verify(uut, Mockito.times(eventCount)).generateOneJson();
    }
}
