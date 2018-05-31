import dataGenerator.Item;
import inputParser.FileInputController;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class FileInputControllerTest {
    FileInputController uut = new FileInputController("src\\test\\resources\\underTest.csv");

    @Test
    public void getRandomItemTest() {
        //given
        BigDecimal amount = BigDecimal.valueOf(431);
        //when
        Item item = uut.getRandomItem(amount);
        BigDecimal sum = BigDecimal.valueOf(431).multiply(BigDecimal.valueOf(2.53));
        //then
        Assert.assertEquals(sum, uut.getSumAndReset());
        Assert.assertEquals("TestObjectNR1", item.getName());
        Assert.assertEquals(BigDecimal.valueOf(431), item.getQuantity());
        Assert.assertEquals(BigDecimal.valueOf(2.53), item.getPrice());
    }

    @Test
    public void getJsonFromNullFile() {
        uut = new FileInputController("notAPath_String[[");
        Item json = uut.getRandomItem(BigDecimal.ZERO);
        Assert.assertEquals(null, json);
    }

}
