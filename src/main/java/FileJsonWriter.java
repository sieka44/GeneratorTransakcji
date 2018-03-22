import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileJsonWriter {
    public void saveJson(JSONObject json, int number, String outDir) {
        try {
            String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
            File file = new File(path + "/json" + (number+1) + ".json");
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }else {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json.toJSONString());
                fileWriter.close();
            }
            //LOGGER
        } catch (IOException e) {
            //LOGGER
            e.printStackTrace();
        }
    }
}
