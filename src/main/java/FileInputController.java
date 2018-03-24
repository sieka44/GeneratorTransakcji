import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FileInputController {
    private List<Product> listOfProducts;
    private Random rnd;
    private double sum = 0.0;

    FileInputController(String path) {
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
                if (currLine != null) {
                    String[] product = currLine.split(",");
                    listOfProducts.add(new Product(product[0], Double.parseDouble(product[1])));
                } else break;
            }

        } catch (IOException e) {
            //LOGGER
            e.printStackTrace();
        }
    }

    public double getSumAndReset() {
        double outcome = sum;
        sum = 0.0;
        return outcome;
    }

    public JSONObject getRandomObject(Integer quantity) {
        JSONObject jsonObject = new JSONObject();
        if (listOfProducts.size() > 0) {
            Product product = listOfProducts.get(rnd.nextInt(listOfProducts.size()));
            jsonObject.put("name", product.getName());
            jsonObject.put("quantity", quantity);
            jsonObject.put("price", product.getPrice());
            sum += (double) ((int) (product.getPrice() * 100) * quantity) / 100;
        }
        return jsonObject;
    }
}
