package main;

import model.Customer;
import model.ECategory;
import model.OrderProduct;
import model.Product;
import org.joda.time.DateTime;
import repository.ProductRepository;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

//        Customer customer = new Customer.Builder()
//                .name("Victor")
//                .surname("Mitoi")
//                .address("Bucuresti")
//                .phone("0745")
//                .birthdate(new DateTime(1999, 06, 19, 0, 0))
//                .build();
//        System.out.println(customer);

        Product product = Product.builder()
                .category(ECategory.IT)
                .description("Laptop")
                .availableQuantity(10)
                .discount(0)
                .price(BigDecimal.valueOf(4000))
                .build();


        Product product2 = Product.builder()
                .category(ECategory.IT)
                .description("Laptop")
                .availableQuantity(10)
                .discount(0)
                .price(BigDecimal.valueOf(4000))
                .build();

        Product product3 = Product.builder()
                .category(ECategory.IT)
                .description("Laptop Asus ROG 2 2021")
                .availableQuantity(25)
                .discount(0)
                .price(BigDecimal.valueOf(5000))
                .build();

        System.out.println(product);

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(10)
                .build();

        try {
            ProductRepository productRepository = ProductRepository.getInstance();
//            productRepository.postProductList(Arrays.asList(product,product2));
            productRepository.editProduct("bdbec9e7-f0aa-40c3-add5-05aea507dc2f", product3);
            Map<String,Product> products = productRepository.readProducts();
            System.out.println(productRepository.findProductByUuid("bdbec9e7-f0aa-40c3-add5-05aea507dc2f"));
            System.out.println(products);
            productRepository.closeFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
