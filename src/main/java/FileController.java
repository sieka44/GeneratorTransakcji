import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileController {
    List<Product> listOfProducts;
    FileController(String file){
        listOfProducts = new LinkedList<>();
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
private JSONObject createOneProduct(String product){
        String[] productData = product.split(",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",productData[0]);
        jsonObject.put("quantity",generateIntegerData(itemsQuantity));
        jsonObject.put("price",productData[1]);

        return jsonObject;

    }

    private JSONArray readFile(){
        try {
            FileReader fileReader = new FileReader(itemsFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            JSONArray products = new JSONArray();
            bufferedReader.readLine();


            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
 */