package fileWriter;

import org.json.simple.JSONObject;

public interface FileWriter {
    void saveData(JSONObject json, int idNumber, String outDir);
}
