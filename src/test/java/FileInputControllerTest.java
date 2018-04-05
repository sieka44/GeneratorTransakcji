import inputParser.FileInputController;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class FileInputControllerTest {
    FileInputController uut = new FileInputController("src\\test\\resources\\underTest.csv");

    @Test
    public void getRandomObjectTest() {
        BigDecimal amount =  BigDecimal.valueOf(431);
        JSONObject json = uut.getRandomObject(amount);
        String name = json.get("name").toString();
        String quantity = json.get("quantity").toString();
        String price = json.get("price").toString();
        BigDecimal sum = BigDecimal.valueOf(431).multiply(BigDecimal.valueOf(2.53));
        Assert.assertEquals(uut.getSumAndReset(), sum);
        Assert.assertEquals("\"TestObjectNR1\"", name);
        Assert.assertEquals("431", quantity);
        Assert.assertEquals("2.53", price);
    }

    @Test
    public void getJsonFromNullFile() {
        uut = new FileInputController("notAPath_String[[");
        JSONObject json = uut.getRandomObject(BigDecimal.ZERO);
        Assert.assertEquals(new JSONObject(), json);
    }

}
