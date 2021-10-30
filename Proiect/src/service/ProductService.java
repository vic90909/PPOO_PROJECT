package service;

import model.ECategory;
import model.Product;
import repository.ProductRepository;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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
            System.out.println("You did not create any product (null)");
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
        if(product!=null) {
            productRepository.logicallyDelete(product);
            System.out.println("The product: "+product.toStringNice()+" has bees logically deleted");
            productRepository.editProduct(uuid, product);
            return;
        }
        System.out.println("The product with the uuid:"+uuid+" it is not present!");
    }

    public void logicallyDelete(String uuid, List<Product> productList) {
        if (!Objects.isNull(productList)) {
            for (Product each : productList) {
                if (each.getUuid().equals(uuid)){
                    each.setLogicallyDeleted(true);
                    System.out.println("The product: "+each.toStringNice()+" has bees logically deleted");
                    return;
                }
            }
            System.out.println("The product with the uuid:"+uuid+" it is not present in the list");
        } else {
            System.out.println("The product list is null");
        }

    }

}
