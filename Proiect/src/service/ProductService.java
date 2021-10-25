package service;

import model.Product;
import repository.ProductRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    ProductRepository productRepository ;

    public ProductService() throws FileNotFoundException {
        this.productRepository = ProductRepository.getInstance();
    }

    public List<Product> getAllProducts(){
        return new ArrayList<Product>(productRepository.readProducts().values());
    }

    public void showProducts(){
        int i=1;
        for(Product each: getAllProducts()){
            System.out.println(i+". :"+each.toString());
            i++;
        }
    }

    public Product findByUuid(String uuid){
        return productRepository.findProductByUuid(uuid);
    }
}
