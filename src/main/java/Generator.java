import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

class Generator {

    FileController fileController;
    private String customerIds;
    private String date;
    private String itemsFile;
    private String itemsCount;
    private String itemsQuantity;
    private String eventsCount;
    private String outDir;

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemsFile(String itemsFile) {
        this.itemsFile = itemsFile;
        fileController = new FileController(this.itemsFile);
    }

    public void setItemsCount(String itemsCount) {
        this.itemsCount = itemsCount;
    }

    public void setItemsQuantity(String itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }

    public void setEventsCount(String eventsCount) {
        this.eventsCount = eventsCount;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }

    private int generateIntegerData(String data) {
        String[] minMax = data.split(":");
        Random rnd = new Random();
        return rnd.nextInt(Integer.parseInt(minMax[1])) + Integer.parseInt(minMax[0]);
    }

    private long getRandomTimeBetweenTwoDates(long beginTime, long endTime) {
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }

    private ZonedDateTime generateDate() {
        //System.out.println(date);
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime outputDate = ZonedDateTime.now().withFixedOffsetZone();
        if (date.length()>29) {
            String start = date.substring(0,28).replace("\"","");
            String end = date.substring(29).replace("\"","");
            ZonedDateTime startDate = ZonedDateTime.parse(start, formatter);
            ZonedDateTime endDate = ZonedDateTime.parse(end, formatter);
            long randomTime = getRandomTimeBetweenTwoDates(Timestamp.valueOf(startDate.toLocalDateTime()).getTime(), Timestamp.valueOf(endDate.toLocalDateTime()).getTime());
            Instant instant = Instant.ofEpochSecond(randomTime);
            outputDate = ZonedDateTime.of(LocalDateTime.ofInstant(instant, startDate.getZone()), startDate.getZone());
        }else {
            System.out.println("błąd w parsowaniu daty");
            //LOGGER
        }
        return outputDate;
    }

    private JSONArray generateItems() {
        int amount = generateIntegerData(itemsCount);
        JSONArray array = new JSONArray();
        for (int i = 0; i < amount; i++) {
            array.add(fileController.getRandomObject(generateIntegerData(itemsQuantity)));
        }
        return array;
    }

    public void generateJson() {
        int events = Integer.parseInt(eventsCount);
        for (int i = 0; i < events; i++) {
            JSONObject json = new JSONObject();
            json.put("id", generateIntegerData("1:10"));
            json.put("timestamp", generateDate().toString());
            json.put("customer_id", generateIntegerData(customerIds));
            JSONArray array = generateItems();
            json.put("items", array);
            json.put("sum", fileController.getSumAndReset());
            //System.out.println(json.toString());
            saveJson(json, i);
        }
    }

    private void saveJson(JSONObject json, int number) {
        try {
            String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
            File file = new File(path + "/json" + (number+1) + ".json");
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }else {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json.toJSONString());
                fileWriter.close();
            }
            //LOGGER
        } catch (IOException e) {
            //LOGGER
            e.printStackTrace();
        }
    }
}