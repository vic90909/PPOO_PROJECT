package service;

import model.ECategory;
import model.Product;
import repository.ProductRepository;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;

import static utils.Constants.PRODUCT_REPORT;

public class ProductService {

    ProductRepository productRepository;

    public ProductService() throws FileNotFoundException {
        this.productRepository = ProductRepository.getInstance();
    }

    public List<Product> getAllProducts() {
        return new ArrayList<Product>(productRepository.readProducts().values());
    }

    public void showProducts() {
        int i = 1;
        for (Product each : getAllProducts()) {
            System.out.println(i + ". :" + each.toString());
            i++;
        }
    }

    public Product findByUuid(String uuid) {
        Product found = productRepository.findProductByUuid(uuid);
        if (found != null) {
            System.out.println("The product with the given uuid: " + uuid + " is: " + found.toStringNice());
        } else {
            System.out.println("No product found with the given uuid: " + uuid + ".");
        }
        return found;
    }

    public Product postProduct(Scanner scanner) throws FileNotFoundException {
        Product newProduct = inputProduct(scanner);

        if (newProduct != null) {
            productRepository.postProduct(newProduct);
        } else {
            System.out.println("You did not create any product (null)");
        }
        return newProduct;
    }

    public Product postProduct(Product product) throws FileNotFoundException {
        if (product != null) {
            productRepository.postProduct(product);
        } else {
            System.out.println("You did not post any product (null)");
        }
        return product;
    }

    public Product putProduct(Scanner scanner, String uuid) throws FileNotFoundException {
        Product newProduct = null;
        Product found = productRepository.findProductByUuid(uuid);
        if (found != null) {
            newProduct = inputProduct(scanner);
            if (newProduct != null) {
                productRepository.editProduct(uuid, newProduct);
            } else {
                System.out.println("You did not edit any product (null)");
            }
        } else {
            System.out.println("There is any product with that uuid:" + uuid);
        }

        return newProduct;
    }

    private Product inputProduct(Scanner scanner) {
        Product newProduct = null;
        do {
            System.out.println("Title: ");
            String title = scanner.nextLine();
            System.out.println("Description: ");
            String description = scanner.nextLine();
            boolean ok = false;

            ECategory eCategory = null;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Category: ");
                    eCategory = ECategory.valueOf(scanner.nextLine());
                } catch (IllegalArgumentException exception) {
                    System.out.println("Wrong argument for category. Try again (IT, FOOD, APPLIANCES, TOYS, FASHION, ENTERTAINMENT)");
                    ok = false;
                }
            }

            ok = false;
            BigDecimal price = BigDecimal.ZERO;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Price: ");
                    price = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
                } catch (IllegalArgumentException exception) {
                    System.out.println("Wrong argument for price. Try again");
                    ok = false;
                }
            }

            ok = false;
            int availableQuantity = 0;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Quantity: ");
                    availableQuantity = Integer.parseInt(scanner.nextLine());
                } catch (IllegalArgumentException exception) {
                    System.out.println("Wrong argument for quantity. Try again");
                    ok = false;
                }
            }

            ok = false;
            float discount = 0f;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Discount: ");
                    discount = Float.parseFloat(scanner.nextLine());
                } catch (IllegalArgumentException exception) {
                    System.out.println("Wrong argument for category. Try again");
                    ok = false;
                }
            }

            newProduct = new Product(title, description, eCategory, price, availableQuantity, discount);
            System.out.println(newProduct.toStringNice());
            System.out.println("This is your choice or do you want to start over? \n Yes to post this product or No to Start over");

        } while (scanner.nextLine().equalsIgnoreCase("No"));
        return newProduct;
    }

    public void logicallyDelete(String uuid) throws FileNotFoundException {
        Product product = productRepository.findProductByUuid(uuid);
        if (product != null) {
            productRepository.logicallyDelete(product);
            System.out.println("The product: " + product.toStringNice() + " has bees logically deleted");
            productRepository.editProduct(uuid, product);
            return;
        }
        System.out.println("The product with the uuid:" + uuid + " it is not present!");
    }

    public void logicallyDelete(String uuid, List<Product> productList) {
        if (!Objects.isNull(productList)) {
            for (Product each : productList) {
                if (each.getUuid().equals(uuid)) {
                    each.setLogicallyDeleted(true);
                    System.out.println("The product: " + each.toStringNice() + " has bees logically deleted");
                    return;
                }
            }
            System.out.println("The product with the uuid:" + uuid + " it is not present in the list");
        } else {
            System.out.println("The product list is null");
        }

    }

    public void writeReport() {
        Product[] products = getAllProducts().toArray(new Product[0]);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(PRODUCT_REPORT.getName());
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(String.format("%50s\n\n", "Products Report"));

            Map<String, Integer> maxLenghts = new HashMap<>();
            maxLenghts.put("uuid", 4);
            maxLenghts.put("title", 5);
            maxLenghts.put("description", 11);
            maxLenghts.put("category", 8);
            maxLenghts.put("price", 5);
            maxLenghts.put("availableQuantity", 18);
            maxLenghts.put("discount", 8);
            maxLenghts.put("createdBy", 8);
            maxLenghts.put("createdDate", 10);
            maxLenghts.put("logicallyDeleted", 17);
            maxLenghts.put("deletedDate", 10);
            maxLenghts.put("editedDate", 9);

            for (int i = 0; i < products.length; i++) {
                if (products[i].getUuid().length() > maxLenghts.get("uuid")) {
                    maxLenghts.put("uuid", products[i].getUuid().length());
                }
                if (products[i].getTitle().length() > maxLenghts.get("title")) {
                    maxLenghts.put("title", products[i].getTitle().length());
                }
                if (products[i].getDescription().length() > maxLenghts.get("description")) {
                    maxLenghts.put("description", products[i].getDescription().length());
                }
                if (products[i].getCategory().getDescription().length() > maxLenghts.get("category")) {
                    maxLenghts.put("category", products[i].getCategory().getDescription().length());
                }
                if (products[i].getPrice().toString().length() > maxLenghts.get("price")) {
                    maxLenghts.put("price", products[i].getPrice().toString().length());
                }
                if (String.valueOf(products[i].getAvailableQuantity()).length() > maxLenghts.get("availableQuantity")) {
                    maxLenghts.put("availableQuantity", String.valueOf(products[i].getAvailableQuantity()).length());
                }
                if (String.valueOf(products[i].getDiscount()).length() > maxLenghts.get("discount")) {
                    maxLenghts.put("discount", String.valueOf(products[i].getDiscount()).length());
                }
                if (products[i].getCreatedBy().length() > maxLenghts.get("createdBy")) {
                    maxLenghts.put("createdBy", products[i].getCreatedBy().length());
                }
                if (products[i].getCreatedDate().toString().length() > maxLenghts.get("createdDate")) {
                    maxLenghts.put("createdDate", products[i].getCreatedDate().toString().length());
                }
                if (products[i].getDeletedDate() != null) {
                    if (products[i].getUuid().length() > maxLenghts.get("deletedDate")) {
                        maxLenghts.put("deletedDate", products[i].getUuid().length());
                    }
                } else {
                    if (19 > maxLenghts.get("deletedDate")) {
                        maxLenghts.put("deletedDate", 19);
                    }
                }

                if (products[i].getEditedDate() != null) {
                    if (products[i].getEditedDate().toString().length() > maxLenghts.get("editedDate")) {
                        maxLenghts.put("editedDate", products[i].getEditedDate().toString().length());
                    }
                } else {
                    if (18 > maxLenghts.get("editedDate")) {
                        maxLenghts.put("editedDate", 18);
                    }
                }

            }

            for (Map.Entry<String, Integer> entry : maxLenghts.entrySet()) {
                if (entry.getValue() % 2 == 1) {
                    entry.setValue(entry.getValue() + 1);
                }
            }


            printWriter.print(String.format(
                    "||%" + (maxLenghts.get("uuid") / 2 - 2 + 5) + "s %-" + (maxLenghts.get("uuid") / 2 + 2 + 5) + "s" +
                            "||%" + (maxLenghts.get("title") / 2 - 3 + 5) + "s %-" + (maxLenghts.get("title") / 2 + 3 + 5) + "s" +
                            "||%" + (maxLenghts.get("description") / 2 - 6 + 5) + "s %-" + (maxLenghts.get("description") / 2 + 6 + 5) + "s" +
                            "||%" + (maxLenghts.get("category") / 2 - 4 + 5) + "s %-" + (maxLenghts.get("category") / 2 + 4 + 5) + "s" +
                            "||%" + (maxLenghts.get("price") / 2 - 3 + 5) + "s %-" + (maxLenghts.get("price") / 2 + 3 + 5) + "s" +
                            "||%" + (maxLenghts.get("availableQuantity") / 2 - 9 + 5) + "s %-" + (maxLenghts.get("availableQuantity") / 2 + 9 + 5) + "s" +
                            "||%" + (maxLenghts.get("discount") / 2 - 4 + 5) + "s %-" + (maxLenghts.get("discount") / 2 + 4 + 5) + "s" +
                            "||%" + (maxLenghts.get("createdBy") / 2 - 5 + 5) + "s %-" + (maxLenghts.get("createdBy") / 2 + 5 + 5) + "s" +
                            "||%" + (maxLenghts.get("createdDate") / 2 - 6 + 5) + "s %-" + (maxLenghts.get("createdDate") / 2 + 6 + 5) + "s" +
                            "||%" + (maxLenghts.get("editedDate") / 2 - 5 + 5) + "s %-" + (maxLenghts.get("editedDate") / 2 + 5 + 5) + "s" +
                            "||%" + (maxLenghts.get("deletedDate") / 2 - 6 + 5) + "s %-" + (maxLenghts.get("deletedDate") / 2 + 6 + 5) + "s" +
                            "||%" + (maxLenghts.get("logicallyDeleted") / 2 - 9 + 5) + "s %-" + (maxLenghts.get("logicallyDeleted") / 2 + 9 + 5) + "s||\n\n"
                    , "", "Uuid"
                    , "", "Title"
                    , "", "Description"
                    , "", "Category"
                    , "", "Price"
                    , "", "Available Quantity"
                    , "", "Discount"
                    , "", "Created By"
                    , "", "Created Date"
                    , "", "Edited Date"
                    , "", "Deleted Date"
                    , "", "Logically Deleted"
            ));

            for (int i = 0; i < products.length; i++) {
                printWriter.print(String.format(
                        "||%" + (maxLenghts.get("uuid") / 2 - products[i].getUuid().length() / 2 + 5) + "s %-" + (maxLenghts.get("uuid") / 2 + products[i].getUuid().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("title") / 2 - products[i].getTitle().length() / 2 + 5) + "s %-" + (maxLenghts.get("title") / 2 + products[i].getTitle().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("description") / 2 - products[i].getDescription().length() / 2 + 5) + "s %-" + (maxLenghts.get("description") / 2 + products[i].getDescription().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("category") / 2 - products[i].getCategory().getDescription().length() / 2 + 5) + "s %-" + (maxLenghts.get("category") / 2 + products[i].getCategory().getDescription().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("price") / 2 - products[i].getPrice().toString().length() / 2 + 5) + "s %-" + (maxLenghts.get("price") / 2 + products[i].getPrice().toString().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("availableQuantity") / 2 - String.valueOf(products[i].getAvailableQuantity()).length() / 2 + 5) + "s %-" + (maxLenghts.get("availableQuantity") / 2 + String.valueOf(products[i].getAvailableQuantity()).length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("discount") / 2 - String.valueOf(products[i].getDiscount()).length() / 2 + 5) + "s %-" + (maxLenghts.get("discount") / 2 + String.valueOf(products[i].getDiscount()).length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("createdBy") / 2 - products[i].getCreatedBy().length() / 2 + 5) + "s %-" + (maxLenghts.get("createdBy") / 2 + products[i].getCreatedBy().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("createdDate") / 2 - products[i].getCreatedDate().toString().length() / 2 + 5) + "s %-" + (maxLenghts.get("createdDate") / 2 + products[i].getCreatedDate().toString().length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("editedDate") / 2 - (products[i].getEditedDate() != null ? products[i].getEditedDate().toString() : "Not edited so far").length() / 2 + 5) + "s %-" + (maxLenghts.get("editedDate") / 2 + (products[i].getEditedDate() != null ? products[i].getEditedDate().toString() : "Not edited so far").length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("deletedDate")/ 2 - (products[i].getDeletedDate() != null ? products[i].getDeletedDate().toString() : "Not deleted so far").length() / 2 + 5) + "s %-" + (maxLenghts.get("deletedDate")/ 2 + (products[i].getDeletedDate() != null ? products[i].getDeletedDate().toString() : "Not deleted so far").length() / 2 + 5) + "s" +
                                "||%" + (maxLenghts.get("logicallyDeleted") / 2 - (products[i].isLogicallyDeleted() ? "Yes" : "No").length() / 2 + 5) + "s %-" + (maxLenghts.get("logicallyDeleted") / 2 + (products[i].isLogicallyDeleted() ? "Yes" : "No").length() / 2 + 5) + "s||\n"
                        , "", products[i].getUuid()
                        , "", products[i].getTitle()
                        , "", products[i].getDescription()
                        , "", products[i].getCategory().getDescription()
                        , "", products[i].getPrice().toString()
                        , "", products[i].getAvailableQuantity()
                        , "", products[i].getDiscount()
                        , "", products[i].getCreatedBy()
                        , "", products[i].getCreatedDate().toString()
                        , "", products[i].getEditedDate() != null ? products[i].getEditedDate().toString() : "Not edited so far"
                        , "", products[i].getDeletedDate() != null ? products[i].getDeletedDate().toString() : "Not deleted so far"
                        , "", products[i].isLogicallyDeleted() ? "Yes" : "No"
                ));
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
