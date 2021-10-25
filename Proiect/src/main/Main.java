package main;

import model.Customer;
import model.ECategory;
import model.OrderProduct;
import model.Product;
import org.joda.time.DateTime;
import repository.ProductRepository;
import service.ProductService;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ProductService productService = new ProductService();

        Customer customer = new Customer.Builder()
                .name("Victor")
                .surname("Mitoi")
                .address("Bucuresti")
                .phone("0745")
                .birthdate(new DateTime(1999, 06, 19, 0, 0))
                .build();
        System.out.println(customer);

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

        System.out.println("Hello there\n Please select your category first (enter the digit/word according to the wanted category):\n");
        Scanner scanner = new Scanner(System.in);
        String s = "";
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
                                String uuid = scanner.nextLine();
                                Product found = productService.findByUuid(uuid);
                                if (found != null) {
                                    System.out.println("The product with the given uuid: " + uuid + " is: " + found.toStringNice());
                                } else {
                                    System.out.println("No product found with the given uuid: " + uuid + ".");
                                }
                                break;
                            case "3":
                                System.out.println("Post a product");
                                break;
                            case "4":
                                System.out.println("Put a product");
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
//        try {
//            ProductRepository productRepository = ProductRepository.getInstance();
////            productRepository.postProductList(Arrays.asList(product,product2));
//            productRepository.editProduct("bdbec9e7-f0aa-40c3-add5-05aea507dc2f", product3);
//            Map<String,Product> products = productRepository.readProducts();
//            System.out.println(productRepository.findProductByUuid("bdbec9e7-f0aa-40c3-add5-05aea507dc2f"));
//            System.out.println(products);
//            productRepository.closeFile();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
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
        System.out.println("=========================================");
    }
}
