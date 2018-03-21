import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

class Generator {

    private String customerIds;
    private String date;
    private String itemsFile;
    private String itemsCount;
    private String itemsQuantity;
    private String eventsCount;
    private String outDir;
    private double sum = 0.0;
    FileController fileController;

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemsFile(String itemsFile) {
        this.itemsFile = itemsFile;
        fileController = new FileController(itemsFile);
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

    private int generateIntegerData(String data){
        String[] minMax = data.split(":");
        Random rnd = new Random();
        return rnd.nextInt(Integer.parseInt(minMax[1]))+Integer.parseInt(minMax[0]);
    }

    private long getRandomTimeBetweenTwoDates (long beginTime, long endTime) {
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }

    private ZonedDateTime generateDate(){
        String[] dates =  date.split(":");
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime startDate = ZonedDateTime.parse(dates[0],formatter);
        ZonedDateTime endDate = ZonedDateTime.parse(dates[1],formatter);
        long randomTime = getRandomTimeBetweenTwoDates(Timestamp.valueOf(startDate.toLocalDateTime()).getTime(),Timestamp.valueOf(endDate.toLocalDateTime()).getTime());
        Instant instant = Instant.ofEpochSecond(randomTime);
        ZonedDateTime outputDate = ZonedDateTime.of(LocalDateTime.ofInstant(instant,startDate.getZone()),startDate.getZone());

        return outputDate;
    }

    public boolean generateJson(){
        JSONObject json = new JSONObject();
        json.put("id",generateIntegerData("1:10"));
        json.put("timestamp",generateDate());
        json.put("customer_id",generateIntegerData(customerIds));
        JSONArray array = null;

        return false;
    }
}
/*
        -customerIds 1:20
        -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100"
        -itemsFile items.csv
        -itemsCount 5:15
        -itemsQuantity 1:30
        -eventsCount 1000
        -outDir ./output
{
  "id": 1,
  "timestamp": "2018-03-08T12:31:13.000-0100",
  "customer_id": 12,
  "items": [
    {
      "name": "bułeczka",
      "quantity": 3,
      "price": 1.2
    },
    {
      "name": "mleko 3% 1l",
      "quantity": 1,
      "price": 2.3
    }
  ],
  "sum": 4.5
}
 */