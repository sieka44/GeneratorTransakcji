package dataGenerator;

import fileWriter.FileJsonWriter;
import fileWriter.FileWriter;
import inputParser.FileInputController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Generator {
    private static final Logger LOGGER = LogManager.getLogger(Generator.class.getName());

    private FileInputController fileController;
    private String customerIds;
    private String date;
    private String itemsCount;
    private String itemsQuantity;
    private String eventsCount;
    private String outDir;
    private FileWriter writer;

    public Generator(String customerIds, String date, String itemsCount, String itemsQuantity, String eventsCount, String outDir) {
        this.customerIds = customerIds;
        this.date = date;
        this.itemsCount = itemsCount;
        this.itemsQuantity = itemsQuantity;
        this.eventsCount = eventsCount;
        this.outDir = outDir;
        writer = new FileJsonWriter();
    }

    public void setWriter(FileWriter jsonWriter) {
        this.writer = jsonWriter;
    }

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemsCount(String itemsCount) {
        this.itemsCount = itemsCount;
    }

    public void setItemsFile(String itemsFile) {
        fileController = new FileInputController(itemsFile);
    }

    public void setFileController(FileInputController fileController) {
        this.fileController = fileController;
    }

    public void setEventsCount(String eventsCount) {
        this.eventsCount = eventsCount;
    }

    private int generateIntegerData(String data) {
        String[] minMax = data.split(":");
        Random rnd = new Random();
        if (minMax.length >= 2) {
            int min = Integer.parseInt(minMax[0]);
            int max = Integer.parseInt(minMax[1]);
            LOGGER.trace("Random value between:" + min + " and " + max);
            return LOGGER.traceExit( min + rnd.nextInt(max - min +1) );
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

    private ZonedDateTime generateDate() {
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
        }
        return LOGGER.traceExit(outputDate);
    }

    private JSONArray generateItems() {
        int amount = generateIntegerData(itemsCount);
        LOGGER.trace("Drawn items quantity: " + amount);
        JSONArray array = new JSONArray();
        for (int i = 0; i < amount; i++) {
            array.add(fileController.getRandomObject(generateIntegerData(itemsQuantity)));
        }
        return LOGGER.traceExit(array);
    }

    public JSONObject generateOneJson() {
        JSONObject json = new JSONObject();
        json.put("id", generateIntegerData("1:10"));
        json.put("timestamp", generateDate().toString());
        json.put("customer_id", generateIntegerData(customerIds));
        JSONArray array = generateItems();
        json.put("items", array);
        json.put("sum", fileController.getSumAndReset());
        LOGGER.info("JSON generated:" + json.toJSONString());
        return json;
    }

    public void generateEvents() {
        int events = Integer.parseInt(eventsCount);
        LOGGER.trace("Events: " + events);
        for (int i = 0; i < events; i++) {
            JSONObject json = generateOneJson();
            writer.saveData(json, i, outDir);
        }
    }

}