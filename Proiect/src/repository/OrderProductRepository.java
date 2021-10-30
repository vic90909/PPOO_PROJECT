package repository;

import model.OrderProduct;
import model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import static utils.Constants.ORDER_PRODUCT_FILENAME;
import static utils.Constants.PRODUCT_FILENAME;

public class OrderProductRepository {
    private PrintWriter printWriter;
    private static OrderProductRepository instance = null;

    private OrderProductRepository() throws FileNotFoundException {
        File productFile = new File(PRODUCT_FILENAME.getName());
        if (productFile.exists()) {
            this.printWriter = new PrintWriter(new FileOutputStream(ORDER_PRODUCT_FILENAME.getName(), true));
        } else {
            this.printWriter = new PrintWriter(ORDER_PRODUCT_FILENAME.getName());
            printWriter.write("uuid,productUuid,quantity,orderUuid\n");
        }
        printWriter.close();
    }

    public static OrderProductRepository getInstance() throws FileNotFoundException {
        if (Objects.isNull(instance)) {
            instance = new OrderProductRepository();
        }
        return instance;
    }

    public void postOrderProduct(List<OrderProduct> orderProductList, String orderUuid) throws FileNotFoundException {
        this.printWriter = new PrintWriter(new FileOutputStream(ORDER_PRODUCT_FILENAME.getName(), true));
        orderProductList.stream().map(a->printWriter.write(a.toCsv(orderUuid)))
        printWriter.write(orderProduct.toString());
        printWriter.close();
    }
}
