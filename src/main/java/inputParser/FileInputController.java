package inputParser;

import dataGenerator.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class FileInputController {
    private static final Logger LOGGER = LogManager.getLogger(FileInputController.class.getName());
    private List<Product> listOfProducts;
    private Random rnd;
    private BigDecimal sum = BigDecimal.ZERO;

    public FileInputController(String path) {
        LOGGER.entry(this);
        listOfProducts = new LinkedList<>();
        rnd = new Random();
        FileReader fileReader = null;
        try {
            File file = new File(path);
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            while (true) {
                String currLine = bufferedReader.readLine();
                LOGGER.debug("Current Line to parse:" + currLine);
                if (currLine != null) {
                    String[] product = currLine.split(",");
                    listOfProducts.add(new Product(product[0], BigDecimal.valueOf(Double.parseDouble(product[1]))));
                } else break;
            }
            LOGGER.info("Successful read file.");

        } catch (FileNotFoundException e) {
            LOGGER.error("No source csv.", new FileNotFoundException("FileNotFoundExcept"));
        } catch (IOException e) {
            LOGGER.error("Unexpected Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public BigDecimal getSumAndReset() {
        BigDecimal outcome = sum;
        sum = BigDecimal.ZERO;
        LOGGER.trace("sum has been reset: " + outcome + " -> " + sum);
        return outcome;
    }

    public Item getRandomItem(BigDecimal quantity) {
        Item item = null;
        if (listOfProducts.size() > 0) {
            Product product = listOfProducts.get(rnd.nextInt(listOfProducts.size()));
            item = new Item(product.getName().replace("\"",""), quantity, product.getPrice());
            sum = item.getPrice().multiply(quantity).add(sum);
            LOGGER.debug("GetRandomObject returned: " + item.toString());
        } else LOGGER.warn("Empty Item.");
        return item;
    }
}
