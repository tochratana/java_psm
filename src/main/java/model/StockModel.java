package model;

import java.util.ArrayList;

public class StockModel {
    private Product[][] stock;
    private final ArrayList<String> history = new ArrayList<>();
    private boolean isInitialized = false;

    public void setupStock(int numStock, int[] catalogCounts) {
        stock = new Product[numStock][];
        for (int i = 0; i < numStock; i++) {
            stock[i] = new Product[catalogCounts[i]];
        }
        isInitialized = true;
        history.add("Stock initialized with " + numStock + " stocks.");
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public Product[][] getStock() {
        return stock;
    }

    public boolean insertProduct(int stockNumber, int catalogNumber, Product product) {
        if (stock == null || stockNumber < 0 || stockNumber >= stock.length ||
                catalogNumber < 0 || catalogNumber >= stock[stockNumber].length) {
            return false;
        }

        if (stock[stockNumber][catalogNumber] == null) {
            stock[stockNumber][catalogNumber] = product;
            history.add("Inserted: " + product + " into Stock[" + (stockNumber+1) + "][" + (catalogNumber+1) + "]");
            return true;
        }
        return false;
    }

    public boolean updateProduct(int stockNumber, int catalogNumber, int quantity, double price) {
        if (stock == null || stockNumber < 0 || stockNumber >= stock.length ||
                catalogNumber < 0 || catalogNumber >= stock[stockNumber].length) {
            return false;
        }

        Product p = stock[stockNumber][catalogNumber];
        if (p != null) {
            String before = p.toString();
            p.setQuantity(quantity);
            p.setPrice(price);
            history.add("Updated: " + before + " to " + p + " at Stock[" + (stockNumber+1) + "][" + (catalogNumber+1) + "]");
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int stockNumber, int catalogNumber) {
        if (stock == null || stockNumber < 0 || stockNumber >= stock.length ||
                catalogNumber < 0 || catalogNumber >= stock[stockNumber].length) {
            return false;
        }

        if (stock[stockNumber][catalogNumber] != null) {
            history.add("Deleted: " + stock[stockNumber][catalogNumber] + " from Stock[" + (stockNumber+1) + "][" + (catalogNumber+1) + "]");
            stock[stockNumber][catalogNumber] = null;
            return true;
        }
        return false;
    }

    public void viewStockStatus() {
        if (!isInitialized || stock == null) {
            System.out.println("Stock not initialized.");
            return;
        }

        System.out.println("\n========== STOCK STATUS ==========");
        for (int i = 0; i < stock.length; i++) {
            System.out.println("Stock [" + (i + 1) + "]:");
            for (int j = 0; j < stock[i].length; j++) {
                System.out.println("  Catalogue [" + (j + 1) + "]: " + (stock[i][j] == null ? "EMPTY" : stock[i][j]));
            }
            System.out.println();
        }
        System.out.println("=================================");
    }

    public void viewInsertionHistory() {
        System.out.println("\n========== OPERATION HISTORY ==========");
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i+1) + ". " + history.get(i));
            }
        }
        System.out.println("======================================");
    }
}