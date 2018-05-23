import dataGenerator.Transaction;
import fileWriter.FileJsonWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class FileJsonWriterTest {
    FileJsonWriter uut = new FileJsonWriter();

    @Test
    public void creationTest() throws FileNotFoundException {
        uut.saveTransaction(new Transaction(1, ZonedDateTime.now().toString(), 1, null, BigDecimal.ZERO), 0, "./testFolder");
        File fileUnderTest = new File("testFolder/json1.json");
        Assert.assertTrue(fileUnderTest.exists());
        if (!fileUnderTest.delete()) throw new FileNotFoundException();
    }
}
