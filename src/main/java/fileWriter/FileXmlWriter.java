package fileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.XML;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class FileXmlWriter implements FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileXmlWriter.class.getName());

    @Override
    public void saveData(JSONObject json, int idNumber, String outDir) {
        try {
            String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
            File file = new File(path + "/xml" + (idNumber + 1) + ".xml");
            LOGGER.trace("Path to save dir: " + path);
            File parent = file.getParentFile();
            parent.mkdirs();
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            String jsonAsXml = XML.toString(json);
            fileWriter.write(jsonAsXml);
            fileWriter.close();
            LOGGER.trace("XML" + idNumber + " saved successfully");

        } catch (IOException e) {
            LOGGER.error("Error with saving Yaml file nr : " + idNumber);
            e.printStackTrace();
        }
    }
}
