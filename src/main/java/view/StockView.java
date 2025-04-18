package view;

import java.util.Scanner;

public class StockView {
    private final Scanner scanner = new Scanner(System.in);

    public Scanner getScanner() {
        return scanner;
    }

    public void showMenu() {
        System.out.println("\n========== Product Stock Manager ==========");
        System.out.println("1. Set Up Stock");
        System.out.println("2. View Product");
        System.out.println("3. Insert Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");
        System.out.println("6. View Insertion History");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    public void closeScanner() {
        scanner.close();
    }
}