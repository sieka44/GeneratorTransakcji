import org.json.simple.JSONArray;
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
        generator = new Generator("", "", "", "", "", "");
    }

    @Test(expected = NullPointerException.class)
    public void wrongDateTest() {
        FileInputController fileController = Mockito.mock(FileInputController.class);
        Mockito.when(fileController.getSumAndReset()).thenReturn(0.0);
        generator.setFileController(fileController);
        generator.generateOneJson();
    }

    @Test
    public void correctDataTest() {
        FileInputController fileInputController = Mockito.mock(FileInputController.class);
        Mockito.when(fileInputController.getSumAndReset()).thenReturn(0.0);
        generator.setFileController(fileInputController);
        generator.setCustomerIds("1:5");
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime startDate = ZonedDateTime.now().withFixedOffsetZone();
        ZonedDateTime endDate = startDate.plusDays(1);
        generator.setDate(startDate.format(formatter) + ":" + endDate.format(formatter));
        generator.setItemsCount("1:5");
        //when
        JSONObject json = generator.generateOneJson();
        int customerID = Integer.parseInt(json.get("customer_id").toString());
        ZonedDateTime dateTime = ZonedDateTime.parse(json.get("timestamp").toString());
        int itemsCount = ((JSONArray) json.get("items")).size();
        //then
        Assert.assertTrue(0 < customerID && customerID <= 5);
        Assert.assertTrue(dateTime.isAfter(startDate) && dateTime.isBefore(endDate));// && startDate.isBefore(endDate));
        Assert.assertTrue(0 < itemsCount && itemsCount <= 5);
    }

    @Test
    public void generateEventsTest() {
        int eventCount = 10;
        generator.setEventsCount(Integer.toString(eventCount));
        FileJsonWriter jsonWriter = Mockito.mock(FileJsonWriter.class);
        Generator uut = Mockito.spy(generator);
        Mockito.doReturn(new JSONObject()).when(uut).generateOneJson();
        uut.setJsonWriter(jsonWriter);
        uut.generateEvents();
        Mockito.verify(uut, Mockito.times(eventCount)).generateOneJson();
    }
}
