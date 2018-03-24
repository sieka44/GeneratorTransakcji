import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class FileJsonWriterTest {
    FileJsonWriter uut = new FileJsonWriter();

    @Test
    public void creationTest() throws FileNotFoundException {
        uut.saveJson(new JSONObject(), 0, "./testFolder");
        File fileUnderTest = new File("testFolder/json1.json");
        Assert.assertTrue(fileUnderTest.exists());
        if (!fileUnderTest.delete()) throw new FileNotFoundException();
    }

//    @Test
//    public void wrongDirTest() {
//        uut.saveJson(new JSONObject(), 10, "./testFolder/test1/cos/nie");
//        File file = File
//    }
}
