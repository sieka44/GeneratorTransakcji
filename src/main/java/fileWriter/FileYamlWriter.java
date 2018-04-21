package fileWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service("yaml")
public class FileYamlWriter implements fileWriter.FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileYamlWriter.class.getName());

    @Override
    public void saveData(JSONObject json, int idNumber, String outDir) {
        try {
            String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
            File file = new File(path + "/yaml" + (idNumber + 1) + ".yaml");
            LOGGER.trace("Path to save dir: " + path);
            File parent = file.getParentFile();
            parent.mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            JsonNode jsonNodeTree = new ObjectMapper().readTree(json.toString());
            String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
            fileWriter.write(jsonAsYaml);
            fileWriter.close();
            LOGGER.trace("YAML" + idNumber + " saved successfully");
        } catch (IOException e) {
            LOGGER.error("Error with saving Yaml file nr : " + idNumber);
            e.printStackTrace();
        }
    }
}
