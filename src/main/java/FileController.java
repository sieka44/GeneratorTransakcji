import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FileController {
    List<Product> listOfProducts;
    Random rnd;
    double sum = 0.0;

    public double getSumAndReset() {
        double outcome = sum;
        sum=0.0;
        return outcome;
    }

    FileController(String file){
        listOfProducts = new LinkedList<>();
        rnd = new Random();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            while (true){
                String currLine = bufferedReader.readLine();
                String[] product = currLine.split(",");
                if(product.length>=2){
                    listOfProducts.add(new Product(product[0],Double.parseDouble(product[1])));
                }else break;
            }

        } catch (FileNotFoundException e) {
            //LOGGER
            e.printStackTrace();
        } catch (IOException e) {
            //LOGGER
            e.printStackTrace();
        }
    }

    public JSONObject getRandomObject(int quantity){
        JSONObject jsonObject = new JSONObject();
        if(listOfProducts.size()>0) {
            Product product = listOfProducts.get(rnd.nextInt(listOfProducts.size()));
            jsonObject.put("name", product.getName());
            jsonObject.put("quantity", quantity);
            jsonObject.put("price", product.getPrice());
            sum+=quantity*product.getPrice();
        }
        return jsonObject;
    }
}
