package utils;

public enum Constants {
    PRODUCT_FILENAME("Product.csv"),
    CUSTOMER_FILENAME("Customer.bin"),
    ORDER_PRODUCT_FILENAME("OrderProduct.csv"),
    ORDER("Order.csv"),
    PRODUCT_REPORT("Reports/Products Report.txt");

    private String name;

    Constants(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
