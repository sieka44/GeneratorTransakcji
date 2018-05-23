package fileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataGenerator.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service("json")
public class FileJsonWriter implements fileWriter.FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileJsonWriter.class.getName());

    @Override
    public void saveTransaction(Transaction transaction, int idNumber, String outDir) {
        ObjectMapper mapper = new ObjectMapper();
        String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
        File file = new File(path + "/json" + (idNumber + 1) + ".json");
        file.getParentFile().mkdirs();
        try {
            mapper.writeValue(file, transaction);
        } catch (IOException e) {
            LOGGER.error("Error with saving json file nr : " + idNumber);
            e.printStackTrace();
        }
    }
}
