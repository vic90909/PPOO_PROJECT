package repository;

import model.Order;
import model.OrderProduct;
import model.Product;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Constants.ORDER_PRODUCT_FILENAME;
import static utils.Constants.PRODUCT_FILENAME;

public class OrderProductRepository {
    private PrintWriter printWriter;
    private static OrderProductRepository instance = null;

    private OrderProductRepository() throws FileNotFoundException {
        File productFile = new File(ORDER_PRODUCT_FILENAME.getName());
        if (productFile.exists()) {
            this.printWriter = new PrintWriter(new FileOutputStream(ORDER_PRODUCT_FILENAME.getName(), true));
        } else {
            this.printWriter = new PrintWriter(ORDER_PRODUCT_FILENAME.getName());
            printWriter.write("uuid,productUuid,quantity,orderUuid,createdDate,createdBy,editedDate,deletedDate,logicallyDeleted\n");

        }
        printWriter.close();
    }

    public static OrderProductRepository getInstance() throws FileNotFoundException {
        if (Objects.isNull(instance)) {
            instance = new OrderProductRepository();
        }
        return instance;
    }

    public void postOrderProduct(List<OrderProduct> orderProductList) throws FileNotFoundException {
        this.printWriter = new PrintWriter(new FileOutputStream(ORDER_PRODUCT_FILENAME.getName(), true));
        orderProductList.forEach(a -> printWriter.write(a.toCsv()));
        printWriter.close();
    }

    public List<OrderProduct> readOrderProducts() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(ORDER_PRODUCT_FILENAME.getName()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] productString = line.split(splitBy);
                OrderProduct orderProduct = fromStringToOrder(productString);
                if (!orderProduct.isLogicallyDeleted()) {
                    orderProductList.add(orderProduct);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderProductList;
    }

    public List<OrderProduct> findOrderProductsByUuid(String orderUuid) {
        List<OrderProduct> orderProductList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(ORDER_PRODUCT_FILENAME.getName()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] productString = line.split(splitBy);
                OrderProduct orderProduct = fromStringToOrder(productString);
                if (!orderProduct.isLogicallyDeleted() && orderProduct.getOrder().getUuid().equals(orderUuid)) {
                    orderProductList.add(orderProduct);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderProductList;
    }

    private OrderProduct fromStringToOrder(String[] productString) {
        String uuid = productString[0];
        String productUuid = productString[1];
        Integer quantity = Integer.valueOf(productString[2]);
        String orderUuid = productString[3];
        DateTime createdDate = DateTime.parse(productString[4]);
        String createdBy = productString[5];
        DateTime editedDate = productString[6].equals("null") ? null : DateTime.parse(productString[9]);
        DateTime deletedDate = productString[7].equals("null") ? null : DateTime.parse(productString[10]);
        boolean logicallyDeleted = productString[8].equals("Yes");
        Order order = new Order();
        order.setUuid(orderUuid);

        Product product = new Product();
        product.setUuid(productUuid);

        return new OrderProduct(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate, product, quantity, order);
    }
}
