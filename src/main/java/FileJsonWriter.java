import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileJsonWriter {
    public void saveJson(JSONObject json, int idNumber, String outDir) {
        try {
            String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
            File file = new File(path + "/json" + (idNumber + 1) + ".json");
            File parent = file.getParentFile();
            parent.mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toJSONString());
            fileWriter.close();
            //LOGGER
        } catch (IOException e) {
            //LOGGER
            e.printStackTrace();
        }
    }
}
