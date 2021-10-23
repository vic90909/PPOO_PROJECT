package utils;

public enum Constants {
    PRODUCT_FILENAME("Product.csv");

    private String name;

    Constants(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
