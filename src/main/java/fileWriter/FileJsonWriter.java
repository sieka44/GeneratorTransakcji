package fileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service("json")
public class FileJsonWriter implements fileWriter.FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileJsonWriter.class.getName());

    @Override
    public void saveData(JSONObject json, int idNumber, String outDir) {
        try {
            String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
            File file = new File(path + "/json" + (idNumber + 1) + ".json");
            LOGGER.trace("Path to save dir: " + path);
            File parent = file.getParentFile();
            parent.mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toJSONString());
            fileWriter.close();
            LOGGER.trace("JSON" + idNumber + " saved successfully");
        } catch (IOException e) {
            LOGGER.error("Error with saving json file nr : " + idNumber);
            e.printStackTrace();
        }
    }
}
