package repository;

import model.ECategory;
import model.Product;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import static utils.Constants.PRODUCT_FILENAME;

public class ProductRepository {

    private PrintWriter printWriter;
    private static ProductRepository instance = null;

    private ProductRepository() throws FileNotFoundException {
        File productFile = new File(PRODUCT_FILENAME.getName());
        if (productFile.exists()) {
            this.printWriter = new PrintWriter(new FileOutputStream(PRODUCT_FILENAME.getName(), true));
        } else {
            this.printWriter = new PrintWriter(PRODUCT_FILENAME.getName());
            printWriter.write("uuid,title,description,category,price,quantity,discount,createdDate,createdBy,editedDate,deletedDate,logicallyDeleted\n");
        }
        printWriter.close();
    }

    public static ProductRepository getInstance() throws FileNotFoundException {
        if (Objects.isNull(instance)) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public void postProduct(Product product) throws FileNotFoundException {
        this.printWriter = new PrintWriter(new FileOutputStream(PRODUCT_FILENAME.getName(), true));
        printWriter.write(product.toString());
        printWriter.close();
    }

    public void postProductList(List<Product> productList) throws FileNotFoundException {
        this.printWriter = new PrintWriter(new FileOutputStream(PRODUCT_FILENAME.getName(), true));
        for (Product each : productList) {
            printWriter.write(each.toString());
        }
        printWriter.close();
    }

    public Map<String, Product> readProducts() {
        Map<String, Product> products = new HashMap<>();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILENAME.getName()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] productString = line.split(splitBy);
                Product product = fromStringToProduct(productString);
                if (!product.isLogicallyDeleted()) {
                    products.put(product.getUuid(), product);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findProductByUuid(String uuid) {
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILENAME.getName()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] productString = line.split(splitBy);
                Product product = fromStringToProduct(productString);
                if (product.getUuid().equals(uuid) && !product.isLogicallyDeleted()) {
                    return product;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product fromStringToProduct(String[] productString) {
        String uuid = productString[0];
        String title = productString[1];
        String description = productString[2];
        ECategory category = ECategory.valueOf(productString[3]);
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(productString[4]));
        Integer quantity = Integer.valueOf(productString[5]);
        Float discount = Float.valueOf(productString[6]);
        DateTime createdDate = DateTime.parse(productString[7]);
        String createdBy = productString[8];
        DateTime editedDate = productString[9].equals("null") ? null : DateTime.parse(productString[9]);
        DateTime deletedDate = productString[10].equals("null") ? null : DateTime.parse(productString[10]);
        boolean logicallyDeleted = productString[11].equals("Yes");
        return new Product(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate, title, description, category, price, quantity, discount);
    }

    private void resetCsv() throws FileNotFoundException {
        this.printWriter = new PrintWriter(PRODUCT_FILENAME.getName());
        printWriter.write("uuid,title,description,category,price,quantity,discount,createdDate,createdBy,editedDate,deletedDate,logicallyDeleted\n");
        printWriter.close();
    }

    public void editProduct(String uuid, Product newProduct) throws FileNotFoundException {
        Product oldProduct = findProductByUuid(uuid);
        if (oldProduct != null) {
            newProduct.setEditedDate(DateTime.now());
            newProduct.setUuid(uuid);
            Map<String, Product> products = readProducts();
            products.replace(uuid,newProduct);
            resetCsv();
            postProductList(new ArrayList<Product>(products.values()));
        }else{
            System.out.println("Product not found for uuid:"+uuid);
        }
    }

    public void logicallyDelete(Product product){
        product.setLogicallyDeleted(true);
        product.setDeletedDate(DateTime.now());
    }

    public void closeFile() {
        printWriter.close();
    }
}
