package main;

import model.Customer;
import model.ECategory;
import model.OrderProduct;
import model.Product;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        Customer customer = new Customer.Builder()
                .name("Victor")
                .surname("Mitoi")
                .address("Bucuresti")
                .phone("0745")
                .birthdate(new DateTime(1999,06,19,0,0))
                .build();
        System.out.println(customer);

        Product product = Product.builder()
                .category(ECategory.IT)
                .description("Laptop")
                .availableQuantity(10)
                .discount(0)
                .price(BigDecimal.valueOf(4000))
                .build();

        System.out.println(product);

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(10)
                .build();

    }
}
