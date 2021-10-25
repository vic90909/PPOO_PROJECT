package model;

import lombok.Builder;
import model.audit.Auditable;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public class Product extends Auditable implements Serializable {

    private String title;
    private String description;
    private ECategory category;
    private BigDecimal price;
    private int availableQuantity;
    private float discount;

    public Product(){

    }

    public Product(String uuid,String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate) {
        super(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
    }

    public Product(String title, String description, ECategory category, BigDecimal price, int availableQuantity, float discount) {
        super();
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.discount = discount;
    }

    public Product(String uuid, String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate,  String title, String description, ECategory category, BigDecimal price, int availableQuantity, float discount) {
        super(uuid, createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void copyFrom(Product product){
        this.setAvailableQuantity(product.getAvailableQuantity());
        this.setTitle(product.getTitle());
        this.setCategory(product.getCategory());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setDiscount(product.getDiscount());
        this.setCreatedBy(product.getCreatedBy());
        this.setCreatedDate(product.getCreatedDate());
        this.setEditedDate(DateTime.now());
        this.setDeletedDate(product.getDeletedDate());
        this.setLogicallyDeleted(product.isLogicallyDeleted());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.getUuid()).append(",");
        builder.append(title).append(",");
        builder.append(description).append(",");
        builder.append(category.getType()).append(",");
        builder.append(price).append(",");
        builder.append(availableQuantity).append(",");
        builder.append(discount).append(",");
        builder.append(super.toString());
        return builder.toString();
    }

    public String toStringNice() {
        final StringBuilder sb = new StringBuilder("\nProduct:");
        sb.append("\n   uuid='").append(getUuid()).append('\'');
        sb.append("\n   title='").append(title).append('\'');
        sb.append("\n   description='").append(description).append('\'');
        sb.append("\n   category=").append(category);
        sb.append("\n   price=").append(price);
        sb.append("\n   availableQuantity=").append(availableQuantity);
        sb.append("\n   discount=").append(discount);
        sb.append(super.toStringNice());
        return sb.toString();
    }
}
