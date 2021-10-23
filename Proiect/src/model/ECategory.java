package model;


public enum ECategory {

    FOOD("food","Different types of foods"),
    APPLIANCES("appliances", "Home appliances"),
    IT("it","Laptops, Tablets, Phones, Peripherals, Software"),
    TOYS("toys","Toys for children and babies"),
    FASHION("fashion", "Fashion"),
    ENTERTAINMENT("entertainment", "TV, Audio, Video, Gaming, Books");

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
