package fileWriter;

import dataGenerator.Transaction;

public interface FileWriter {
    void saveTransaction(Transaction transaction, int idNumber, String outDir);
    String getTransactionAsString(Transaction transaction);
}
