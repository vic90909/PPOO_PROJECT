package repository;

import model.Customer;
import model.Order;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Constants.ORDER;
import static utils.Constants.ORDER_PRODUCT_FILENAME;

public class OrderRepository {
    private PrintWriter printWriter;
    private static OrderRepository instance = null;

    private OrderRepository() throws FileNotFoundException {
        File productFile = new File(ORDER.getName());
        if (productFile.exists()) {
            this.printWriter = new PrintWriter(new FileOutputStream(ORDER.getName(), true));
        } else {
            this.printWriter = new PrintWriter(ORDER.getName());
            printWriter.write("uuid,customerUuid,discount,totalPrice,createdDate,createdBy,editedDate,deletedDate,logicallyDeleted\n");
        }
        printWriter.close();
    }

    public static OrderRepository getInstance() throws FileNotFoundException {
        if (Objects.isNull(instance)) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public void postOrder(Order order) throws FileNotFoundException {
        this.printWriter = new PrintWriter(new FileOutputStream(ORDER.getName(), true));
        printWriter.write(order.toCsv());
        printWriter.close();
    }

    public List<Order> findOrderByUuid(String orderUuid) {
        List<Order> orderList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(ORDER.getName()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] orderString = line.split(splitBy);
                Order order = fromStringToOrder(orderString);
                if (!order.isLogicallyDeleted() && order.getUuid().equals(orderUuid)) {
                    orderList.add(order);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public List<Order> readAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(ORDER.getName()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] orderString = line.split(splitBy);
                Order order = fromStringToOrder(orderString);
                if (!order.isLogicallyDeleted()) {
                    orderList.add(order);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    private Order fromStringToOrder(String[] productString) {
        String uuid = productString[0];
        String customerUuid = productString[1];
        BigDecimal discount = BigDecimal.valueOf(Long.parseLong(productString[2]));
        BigDecimal totalPrice = BigDecimal.valueOf(Long.parseLong(productString[3]));
        DateTime createdDate = DateTime.parse(productString[4]);
        String createdBy = productString[5];
        DateTime editedDate = productString[6].equals("null") ? null : DateTime.parse(productString[9]);
        DateTime deletedDate = productString[7].equals("null") ? null : DateTime.parse(productString[10]);
        boolean logicallyDeleted = productString[8].equals("Yes");
        Customer customer = new Customer();
        customer.setUuid(customerUuid);

        return new Order(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate, new ArrayList<>(), customer, discount, totalPrice);
    }
}
