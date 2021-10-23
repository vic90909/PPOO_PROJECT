package model;

import lombok.Builder;
import model.audit.Auditable;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public class Product extends Auditable implements Serializable {

    private Long id;
    private String title;
    private String description;
    private ECategory category;
    private BigDecimal price;
    private int availableQuantity;
    private float discount;

    public Product(){
        super();
    }

    public Product(String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate) {
        super(createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
    }

    public Product(Long id, String title, String description, ECategory category, BigDecimal price, int availableQuantity, float discount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.discount = discount;
    }

    public Product(String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate, Long id, String title, String description, ECategory category, BigDecimal price, int availableQuantity, float discount) {
        super(createdBy, createdDate, logicallyDeleted, deletedDate, editedDate);
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }


}
