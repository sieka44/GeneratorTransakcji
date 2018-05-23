package fileWriter;

import dataGenerator.Transaction;

public interface FileWriter {
    //    void saveData(JSONObject json, int idNumber, String outDir);
    void saveTransaction(Transaction transaction, int idNumber, String outDir);
}
