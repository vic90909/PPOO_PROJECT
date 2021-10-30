package model;

import exceptions.NotEnoughProductForOrderException;
import lombok.Builder;
import model.audit.Auditable;

@Builder
public class OrderProduct extends Auditable {
    private static final long serialVersionUID = -6284570010500946123L;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderProduct{");
        sb.append("product=").append(product);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }

    public String toCsv(String orderUuid){
        StringBuilder builder = new StringBuilder();
        builder.append(super.getUuid()).append(",");
        builder.append(product.getUuid()).append(",");
        builder.append(quantity).append(",");
        builder.append(orderUuid).append(",");
        builder.append(super.toString());
        return builder.toString();
    }
}
