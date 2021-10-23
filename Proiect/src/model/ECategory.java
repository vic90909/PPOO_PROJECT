package model;


public enum ECategory {

    FOOD("FOOD","Different types of foods"),
    APPLIANCES("APPLIANCE", "Home appliances"),
    IT("IT","Laptops, Tablets, Phones, Peripherals, Software"),
    TOYS("TOYS","Toys for children and babies"),
    FASHION("FASHION", "Fashion"),
    ENTERTAINMENT("ENTERTAINMENT", "TV, Audio, Video, Gaming, Books");

    private String type;
    private String description;

    ECategory(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
