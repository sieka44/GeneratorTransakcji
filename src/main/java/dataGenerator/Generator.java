package dataGenerator;

import controler.MqController;
import fileWriter.FileWriter;
import inputParser.FileInputController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class Generator {
    private static final Logger LOGGER = LogManager.getLogger(Generator.class.getName());
    @Autowired
    private FileInputController fileInputController;
    private FileWriter writer;
    private String customerIds;
    private String date;
    private String itemsCount;
    private String itemsQuantity;
    private String eventsCount;
    private String outDir;
    private MqController mqController;


    public Generator(String customerIds, String date, String itemsCount, String itemsQuantity, String eventsCount, String outDir, FileWriter fileWriter, MqController controller) {
        this.customerIds = customerIds;
        this.date = date;
        this.itemsCount = itemsCount;
        this.itemsQuantity = itemsQuantity;
        this.eventsCount = eventsCount;
        this.outDir = outDir;
        this.writer = fileWriter;
        this.mqController = controller;
    }

    private int generateIntegerData(String data) {
        String[] minMax = data.split(":");
        Random rnd = new Random();
        if (minMax.length >= 2) {
            int min = Integer.parseInt(minMax[0]);
            int max = Integer.parseInt(minMax[1]);
            LOGGER.trace("Random value between:" + min + " and " + max);
            return LOGGER.traceExit(min + rnd.nextInt(max - min + 1));
        } else {
            LOGGER.error("Cannot parse ot int: " + data);
            return LOGGER.traceExit(0);
        }
    }

    private long getRandomTimeBetweenTwoDates(long beginTime, long endTime) {
        LOGGER.trace("Start long:" + beginTime + ", end long " + endTime);
        long diff = endTime - beginTime + 1;
        return LOGGER.traceExit(beginTime + (long) (Math.random() * diff));
    }

    private String generateDate() {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime outputDate = null;
        if (date.length() > 29) {
            String start = date.substring(0, 28).replace("\"", "");
            String end = date.substring(29).replace("\"", "");
            ZonedDateTime startDate = ZonedDateTime.parse(start, formatter);
            ZonedDateTime endDate = ZonedDateTime.parse(end, formatter);
            LOGGER.trace("Start date : " + startDate + ", end date : " + endDate);
            long randomTime = getRandomTimeBetweenTwoDates(Timestamp.valueOf(startDate.toLocalDateTime()).getTime(), Timestamp.valueOf(endDate.toLocalDateTime()).getTime());
            Instant instant = Instant.ofEpochMilli(randomTime);
            outputDate = ZonedDateTime.of(LocalDateTime.ofInstant(instant, startDate.getZone()), startDate.getZone());
        } else {
            LOGGER.error("Cannot parse to date: " + date);
            return "";
        }
        return LOGGER.traceExit(outputDate.toString());
    }

    private Item[] generateItemsTable() {
        int amount = generateIntegerData(itemsCount);
        LOGGER.trace("Drawn items quantity: " + amount);
        Item[] itemArray = new Item[amount];
        for (int i = 0; i < amount; i++) {
            itemArray[i] = fileInputController.getRandomItem(BigDecimal.valueOf(generateIntegerData(itemsQuantity)));
        }
        return LOGGER.traceExit(itemArray);
    }

    public Transaction generateOneTransaction(int id) {
        return new Transaction(
                id,
                generateDate(),
                generateIntegerData(customerIds),
                generateItemsTable(),
                fileInputController.getSumAndReset()
        );
    }

    public void generateTransactions() {
        int events = Integer.parseInt(eventsCount);
        LOGGER.trace("Events: " + events);
        for (int i = 0; i < events; i++) {
            Transaction transaction = generateOneTransaction(i);
            writer.saveTransaction(transaction, i, outDir);
            sendTransaction(writer.getTransactionAsString(transaction));
        }
    }

    private void sendTransaction(String transaction){
        mqController.sendTransaction(transaction);
    }

}