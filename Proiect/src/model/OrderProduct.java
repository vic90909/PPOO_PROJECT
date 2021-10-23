package model;

import exceptions.NotEnoughProductForOrderException;
import lombok.Builder;

@Builder
public class OrderProduct {
    private Product product;
    private int quantity;

    public OrderProduct(Product product, int quantity) {
        if(quantity > product.getAvailableQuantity()){
            throw new NotEnoughProductForOrderException();
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
