package fileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dataGenerator.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service("yaml")
public class FileYamlWriter implements fileWriter.FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileYamlWriter.class.getName());

    @Override
    public void saveTransaction(Transaction transaction, int idNumber, String outDir) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
        File file = new File(path + "/yaml" + (idNumber + 1) + ".yaml");
        LOGGER.trace("Path to save dir: " + path);
        File parent = file.getParentFile();
        parent.mkdirs();
        try {
            mapper.writeValue(file, transaction);
            LOGGER.trace("YAML" + idNumber + " saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error with saving Yaml file nr : " + idNumber);
        }
    }
}
