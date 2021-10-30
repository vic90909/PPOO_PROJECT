package model;

import exceptions.NotEnoughProductForOrderException;
import lombok.Builder;
import model.audit.Auditable;
import org.joda.time.DateTime;

@Builder
public class OrderProduct extends Auditable {
    private static final long serialVersionUID = -6284570010500946123L;

    private Product product;
    private int quantity;
    private Order order;

    public OrderProduct(Product product, int quantity) {
        if(quantity > product.getAvailableQuantity()){
            throw new NotEnoughProductForOrderException();
        }
        this.product = product;
        this.quantity = quantity;
    }

    public OrderProduct(Product product, int quantity, Order order) {
        if(quantity > product.getAvailableQuantity()){
            throw new NotEnoughProductForOrderException();
        }
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public OrderProduct(String uuid, String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate, Product product, int quantity, Order order) {
        super(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
        this.product = product;
        this.quantity = quantity;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderProduct{");
        sb.append("product=").append(product);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }

    public String toCsv(){
        StringBuilder builder = new StringBuilder();
        builder.append(super.getUuid()).append(",");
        builder.append(product.getUuid()).append(",");
        builder.append(quantity).append(",");
        builder.append(order.getUuid()).append(",");
        builder.append(super.toString());
        return builder.toString();
    }
}
