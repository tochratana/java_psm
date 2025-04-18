package model;

public interface StockOperation {
    void insertProduct(String name, int quantity, double price);
    void updateProduct(String name, int quantity, double price);
    void deleteProduct(String name);
    void viewProducts();
    void viewInsertionHistory();
}

