package model;

import model.audit.Auditable;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Order extends Auditable implements Serializable {

    private static final long serialVersionUID = -4250948701052710046L;

    private List<OrderProduct> orderProductList;
    private Customer customer;
    private BigDecimal discount;
    private BigDecimal totalPrice;

    public Order(){

    }

    public Order(String uuid,String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate) {
        super(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
    }

    public Order(String uuid,String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate, Long id, List<OrderProduct> orderProductList, Customer customer, BigDecimal discount) {
        super(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
        this.orderProductList = orderProductList;
        this.customer = customer;
        this.discount = discount;
        for(OrderProduct each : orderProductList){
            totalPrice.add(each.getProduct().getPrice().multiply(BigDecimal.valueOf(1-each.getProduct().getDiscount())).multiply(BigDecimal.valueOf(each.getQuantity())));
        }
        totalPrice.multiply(discount);
    }


    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", orderProductList=" + orderProductList +
                ", customer=" + customer +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String toCsv(){
        StringBuilder builder = new StringBuilder();
        builder.append(super.getUuid()).append(",");
        builder.append(customer.getUuid()).append(",");
        builder.append(discount).append(",");
        builder.append(totalPrice).append(",");
        builder.append(super.toString());
        return builder.toString();
    }
}
