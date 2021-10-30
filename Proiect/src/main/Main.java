package main;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.*;
import org.joda.time.DateTime;
import org.junit.platform.commons.util.StringUtils;
import repository.CustomerRepository;
import repository.ProductRepository;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ProductService productService = new ProductService();
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService();

        CustomerRepository customerRepository = CustomerRepository.getInstance();


        Customer customer = new Customer.Builder()
                .name("Victor")
                .surname("Mitoi")
                .address("Bucuresti")
                .phone("0745")
                .birthdate(new DateTime(2001, 06, 19, 0, 0))
                .build();

        Product product = Product.builder()
                .category(ECategory.IT)
                .description("Laptop")
                .availableQuantity(10)
                .discount(0)
                .price(BigDecimal.valueOf(4000))
                .build();


        Product product2 = Product.builder()
                .category(ECategory.IT)
                .description("Samsung Galaxy S21 Ultra")
                .availableQuantity(100)
                .discount(0)
                .price(BigDecimal.valueOf(5000))
                .build();

        Product product3 = Product.builder()
                .category(ECategory.IT)
                .description("Laptop Asus ROG 2 2021")
                .availableQuantity(25)
                .discount(0)
                .price(BigDecimal.valueOf(8000))
                .build();

        String repeated = new String(new char[10]).replace("\0", " ");
        repeated+="a";
        System.out.println(repeated);
        String padded = String.format("%-50s || %-50s", "Victor", "Mitoi", System.lineSeparator());
        String padded2 = String.format("%-50s || %-50s", "Victosfhgfshfghr", "Mitoidfghfdghdfh", System.lineSeparator());

        productService.writeReport();

        System.out.println(padded);
        System.out.println(padded2);
        System.out.println(product);

        Order order = new Order();
        order.setDiscount(BigDecimal.ZERO);
        order.setCustomer(customer);

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(5)
                .order(order)
                .build();

        OrderProduct orderProduct2 = OrderProduct.builder()
                .product(product2)
                .quantity(1)
                .order(order)
                .build();

        order.setOrderProductList(Arrays.asList(orderProduct,orderProduct2));

//        orderService.postOrder(order);

        orderService.showOrders();


        System.out.println("Hello there\n Please select your category first (enter the digit/word according to the wanted category):");
        Scanner scanner = new Scanner(System.in);
        String s = "";
        String uuid;
        while (!s.equalsIgnoreCase("Bye-Bye")) {
            showMenu();
            System.out.println("Choose: ");
            s = scanner.nextLine();
            switch (s) {
                case "1":
                    while (!s.equalsIgnoreCase("Back")) {
                        showProductsMenu();
                        System.out.println("Choose: ");
                        s = scanner.nextLine();
                        switch (s) {
                            case "1":
                                System.out.println("Get all products:");
                                productService.showProducts();
                                break;
                            case "2":
                                System.out.println("Get a product by uuid");
                                uuid = scanner.nextLine();
                                productService.findByUuid(uuid);
                                break;
                            case "3":
                                System.out.println("Post a product");
                                productService.postProduct(scanner);
                                break;
                            case "4":
                                System.out.println("Put a product");
                                uuid = scanner.nextLine();
                                productService.putProduct(scanner, uuid);
                                break;
                            case "5":
                                System.out.println("Logically delete a product");
                                uuid = scanner.nextLine();
                                productService.logicallyDelete(uuid);
                                break;
                            case "Back":
                                System.out.println("Returning to the main menu");
                                break;
                        }
                    }
                    break;
                case "2":
                    while (!s.equalsIgnoreCase("Back")) {
                        showProductsMenu();
                        System.out.println("Choose: ");
                        s = scanner.nextLine();
                        switch (s) {
                            case "1":
                                System.out.println("Get all orders:");
                                orderService.showOrders();
                                break;
                            case "2":
                                System.out.println("Get a product by uuid");
                                uuid = scanner.nextLine();
                                productService.findByUuid(uuid);
                                break;
                            case "3":
                                System.out.println("Post a product");
                                productService.postProduct(scanner);
                                break;
                            case "4":
                                System.out.println("Put a product");
                                uuid = scanner.nextLine();
                                productService.putProduct(scanner, uuid);
                                break;
                            case "5":
                                System.out.println("Logically delete a product");
                                uuid = scanner.nextLine();
                                productService.logicallyDelete(uuid);
                                break;
                            case "Back":
                                System.out.println("Returning to the main menu");
                                break;
                        }
                    }
                    break;
                case "3":
                    while (!s.equalsIgnoreCase("Back")) {
                        showCustomersMenu();
                        System.out.println("Choose: ");
                        s = scanner.nextLine();
                        switch (s) {
                            case "1":
                                System.out.println("Get all customers:");
                                customerService.showCustomers();
                                break;
                            case "2":
                                System.out.println("Get a customer by uuid");
                                uuid = scanner.nextLine();
                                customerService.findByUuid(uuid);
                                break;
                            case "3":
                                System.out.println("Post a customer");
                                customerService.postCustomer(scanner);
                                break;
                            case "4":
                                System.out.println("Put a customer");
                                uuid = scanner.nextLine();
                                productService.putProduct(scanner, uuid);
                                break;
                            case "5":
                                System.out.println("Logically delete a customer");
                                uuid = scanner.nextLine();
                                productService.logicallyDelete(uuid);
                                break;
                            case "Back":
                                System.out.println("Returning to the main menu");
                                break;
                        }
                    }
                    break;
                case "Bye-Bye":
                    System.out.println("Bye, see you another time my friend");
                    break;
            }
        }

        scanner.close();
    }

    static void showMenu() {
        System.out.println("\n=========================================");
        System.out.println("Bye-Bye: Close");
        System.out.println("1: Products");
        System.out.println("2: Orders");
        System.out.println("3: Customers");
        System.out.println("=========================================");
    }

    static void showProductsMenu() {
        System.out.println("\n=========================================");
        System.out.println("Back: Back to the main menu");
        System.out.println("1: Get all products");
        System.out.println("2: Get a project by uuid");
        System.out.println("3: Post a product");
        System.out.println("4: Put a product");
        System.out.println("5: Logically delete a product");
        System.out.println("=========================================");
    }

    static void showCustomersMenu() {
        System.out.println("\n=========================================");
        System.out.println("Back: Back to the main menu");
        System.out.println("1: Get all customers");
        System.out.println("2: Get a customer by uuid");
        System.out.println("3: Post a customer");
        System.out.println("4: Put a customer");
        System.out.println("5: Logically delete a customer");
        System.out.println("=========================================");
    }

    static void showOrdersMenu() {
        System.out.println("\n=========================================");
        System.out.println("Back: Back to the main menu");
        System.out.println("1: Get all orders");
        System.out.println("2: Get a order by uuid");
        System.out.println("3: Post a order");
        System.out.println("4: Put a order");
        System.out.println("5: Logically delete a order");
        System.out.println("=========================================");
    }
}
