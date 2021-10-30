package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Objects;

import static utils.Constants.ORDER_PRODUCT_FILENAME;
import static utils.Constants.PRODUCT_FILENAME;

public class OrderRepository {
    private PrintWriter printWriter;
    private static OrderRepository instance = null;

    private OrderRepository() throws FileNotFoundException {
        File productFile = new File(PRODUCT_FILENAME.getName());
        if (productFile.exists()) {
            this.printWriter = new PrintWriter(new FileOutputStream(ORDER_PRODUCT_FILENAME.getName(), true));
        } else {
            this.printWriter = new PrintWriter(ORDER_PRODUCT_FILENAME.getName());
            printWriter.write("uuid,productUuid,quantity,orderUuid\n");
        }
        printWriter.close();
    }

    public static OrderRepository getInstance() throws FileNotFoundException {
        if (Objects.isNull(instance)) {
            instance = new OrderRepository();
        }
        return instance;
    }
}
