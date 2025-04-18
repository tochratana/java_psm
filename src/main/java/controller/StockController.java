package controller;

import model.Product;
import model.StockModel;
import view.StockView;

public class StockController {
    private final StockModel model = new StockModel();
    private final StockView view = new StockView();

    public void start() {
        boolean running = true;
        while (running) {
            view.showMenu();

            try {
                int option = view.getScanner().nextInt();
                view.getScanner().nextLine(); // Clear buffer

                switch (option) {
                    case 1 -> setupStock();
                    case 2 -> {
                        if (!model.isInitialized()) {
                            System.out.println("Stock not initialized.");
                        } else {
                            model.viewStockStatus();
                        }
                    }
                    case 3 -> insertProduct();
                    case 4 -> updateProduct();
                    case 5 -> deleteProduct();
                    case 6 -> model.viewInsertionHistory();
                    case 7 -> {
                        System.out.println("Exiting...");
                        running = false;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                view.getScanner().nextLine(); // Clear buffer
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Invalid stock or catalogue number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void setupStock() {
        try {
            System.out.print("[+] Insert number of Stock: ");
            int stockCount = view.getScanner().nextInt();

            if (stockCount <= 0) {
                System.out.println("Number of stocks must be positive.");
                return;
            }

            int[] catalogCounts = new int[stockCount];
            System.out.println("'Insert number of catalogue for each stock.'");

            for (int i = 0; i < stockCount; i++) {
                System.out.print("[+] Insert number of catalogue on stock [" + (i + 1) + "]: ");
                int count = view.getScanner().nextInt();

                if (count <= 0) {
                    System.out.println("Number of catalogues must be positive.");
                    return;
                }

                catalogCounts[i] = count;
            }

            model.setupStock(stockCount, catalogCounts);
            System.out.println("Stock setup completed successfully.");
            model.viewStockStatus();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        }
    }

    private void insertProduct() {
        if (!model.isInitialized()) {
            System.out.println("Stock not initialized.");
            return;
        }

        try {
            System.out.print("[+] Insert stock number: ");
            int stockNum = view.getScanner().nextInt() - 1;

            if (stockNum < 0 || stockNum >= model.getStock().length) {
                System.out.println("Invalid stock number.");
                return;
            }

            System.out.print("[+] Insert number of catalogue to put product: ");
            int catNum = view.getScanner().nextInt() - 1;

            if (catNum < 0 || catNum >= model.getStock()[stockNum].length) {
                System.out.println("Invalid catalogue number.");
                return;
            }

            view.getScanner().nextLine(); // consume newline
            System.out.print("[+] Insert Product name: ");
            String name = view.getScanner().nextLine();

            if (name.trim().isEmpty()) {
                System.out.println("Product name cannot be empty.");
                return;
            }

            System.out.print("[+] Insert quantity: ");
            int qty = view.getScanner().nextInt();

            if (qty < 0) {
                System.out.println("Quantity cannot be negative.");
                return;
            }

            System.out.print("[+] Insert price: ");
            double price = view.getScanner().nextDouble();

            if (price < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }

            Product product = new Product(name, qty, price);
            boolean success = model.insertProduct(stockNum, catNum, product);
            System.out.println(success ? "Product has been inserted." : "Catalogue already contains a product.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid stock or catalogue number.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input type. Please try again.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        }
    }

    private void updateProduct() {
        if (!model.isInitialized()) {
            System.out.println("Stock not initialized.");
            return;
        }

        try {
            System.out.print("[+] Insert stock number: ");
            int stockNum = view.getScanner().nextInt() - 1;

            if (stockNum < 0 || stockNum >= model.getStock().length) {
                System.out.println("Invalid stock number.");
                return;
            }

            System.out.print("[+] Insert catalogue number: ");
            int catNum = view.getScanner().nextInt() - 1;

            if (catNum < 0 || catNum >= model.getStock()[stockNum].length) {
                System.out.println("Invalid catalogue number.");
                return;
            }

            System.out.print("[+] Insert new quantity: ");
            int qty = view.getScanner().nextInt();

            if (qty < 0) {
                System.out.println("Quantity cannot be negative.");
                return;
            }

            System.out.print("[+] Insert new price: ");
            double price = view.getScanner().nextDouble();

            if (price < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }

            boolean success = model.updateProduct(stockNum, catNum, qty, price);
            System.out.println(success ? "Product updated." : "No product found in selected catalogue.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid stock or catalogue number.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input type. Please try again.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        }
    }

    private void deleteProduct() {
        if (!model.isInitialized()) {
            System.out.println("Stock not initialized.");
            return;
        }

        try {
            System.out.print("[+] Insert stock number: ");
            int stockNum = view.getScanner().nextInt() - 1;

            if (stockNum < 0 || stockNum >= model.getStock().length) {
                System.out.println("Invalid stock number.");
                return;
            }

            System.out.print("[+] Insert catalogue number: ");
            int catNum = view.getScanner().nextInt() - 1;

            if (catNum < 0 || catNum >= model.getStock()[stockNum].length) {
                System.out.println("Invalid catalogue number.");
                return;
            }

            boolean success = model.deleteProduct(stockNum, catNum);
            System.out.println(success ? "Product deleted." : "No product found in selected catalogue.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid stock or catalogue number.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input type. Please try again.");
            view.getScanner().nextLine(); // Clear the scanner buffer
        }
    }
}