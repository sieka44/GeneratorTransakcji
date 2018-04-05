import fileWriter.FileJsonWriter;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class FileJsonWriterTest {
    FileJsonWriter uut = new FileJsonWriter();

    @Test
    public void creationTest() throws FileNotFoundException {
        uut.saveData(new JSONObject(), 0, "./testFolder");
        File fileUnderTest = new File("testFolder/json1.json");
        Assert.assertTrue(fileUnderTest.exists());
        if (!fileUnderTest.delete()) throw new FileNotFoundException();
    }
}
