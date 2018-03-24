import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class FileInputControllerTest {
    FileInputController uut = new FileInputController("src\\test\\resources\\underTest.csv");

//    @Test(expected = FileNotFoundException.class)
//    public void noFileTest() {
//        uut = new FileInputController("notAPath_String[[");
//
//    }

    @Test
    public void getRandomObjectTest() {
        JSONObject json = uut.getRandomObject(10);
        String name = json.get("name").toString();
        String quantity = json.get("quantity").toString();
        String price = json.get("price").toString();
        double sum = 10 * (2.50);
        Assert.assertEquals(uut.getSumAndReset(), sum, 0.01);
        Assert.assertEquals("\"TestObjectNR1\"", name);
        Assert.assertEquals("10", quantity);
        Assert.assertEquals("2.5", price);
    }

    @Test
    public void getJsonFromNullFile() {
        uut = new FileInputController("notAPath_String[[");
        JSONObject json = uut.getRandomObject(0);
        Assert.assertEquals(new JSONObject(), json);
    }

}
