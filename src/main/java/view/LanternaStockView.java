package view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import model.Product;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LanternaStockView {
    private Terminal terminal;
    private Screen screen;
    private WindowBasedTextGUI textGUI;
    private BasicWindow window;
    private Panel contentPanel;

    // Components
    private Button setupStockButton;
    private Button viewProductButton;
    private Button insertProductButton;
    private Button updateProductButton;
    private Button deleteProductButton;
    private Button viewHistoryButton;
    private Button exitButton;

    public LanternaStockView() {
        try {
            // Initialize terminal, screen and GUI
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Create window to hold the panel
            window = new BasicWindow("Product Stock Manager");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));

            // Create panel to hold components
            contentPanel = new Panel(new GridLayout(1));

            // Create and setup GUI
            textGUI = new MultiWindowTextGUI(screen);
            setupUI();

        } catch (IOException e) {
            System.err.println("Error initializing terminal: " + e.getMessage());
        }
    }

    private void setupUI() {
        // Create a header with title
        Label headerLabel = new Label("PRODUCT STOCK MANAGER")
                .addStyle(SGR.BOLD)
                .setForegroundColor(TextColor.ANSI.BLUE);
        contentPanel.addComponent(headerLabel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false)));

        // Add separator
        contentPanel.addComponent(new Separator(Direction.HORIZONTAL)
                .setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.FILL,
                        GridLayout.Alignment.CENTER,
                        true,
                        false)));

        // Add menu options
        setupStockButton = new Button("1. Set Up Stock", this::onSetupStockClicked);
        viewProductButton = new Button("2. View Products", this::onViewProductClicked);
        insertProductButton = new Button("3. Insert Product", this::onInsertProductClicked);
        updateProductButton = new Button("4. Update Product", this::onUpdateProductClicked);
        deleteProductButton = new Button("5. Delete Product", this::onDeleteProductClicked);
        viewHistoryButton = new Button("6. View History", this::onViewHistoryClicked);
        exitButton = new Button("7. Exit", this::onExitClicked);

        List<Button> buttons = Arrays.asList(
                setupStockButton, viewProductButton, insertProductButton,
                updateProductButton, deleteProductButton, viewHistoryButton, exitButton);

        for (Button button : buttons) {
            button.setLayoutData(GridLayout.createLayoutData(
                    GridLayout.Alignment.FILL,
                    GridLayout.Alignment.CENTER,
                    true,
                    false));
            contentPanel.addComponent(button);
        }

        window.setComponent(contentPanel);
    }

    public void show() {
        textGUI.addWindowAndWait(window);
    }

    public void close() {
        try {
            screen.close();
            terminal.close();
        } catch (IOException e) {
            System.err.println("Error closing terminal: " + e.getMessage());
        }
    }

    // Button handlers
    private void onSetupStockClicked() {
        setupStockDialog();
    }

    private void onViewProductClicked() {
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle("Not Implemented")
                .setText("This feature will display stock status")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);
    }

    private void onInsertProductClicked() {
        insertProductDialog();
    }

    private void onUpdateProductClicked() {
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle("Not Implemented")
                .setText("This feature will update products")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);
    }

    private void onDeleteProductClicked() {
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle("Not Implemented")
                .setText("This feature will delete products")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);
    }

    private void onViewHistoryClicked() {
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle("Not Implemented")
                .setText("This feature will display operation history")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);
    }

    private void onExitClicked() {
        window.close();
    }

    // Dialog implementations
    private void setupStockDialog() {
        // Create dialog
        BasicWindow dialog = new BasicWindow("Set Up Stock");
        dialog.setHints(Arrays.asList(Window.Hint.CENTERED, Window.Hint.MODAL));

        Panel dialogPanel = new Panel(new GridLayout(2));

        // Number of stocks
        dialogPanel.addComponent(new Label("Number of Stocks: "));
        TextBox stockCountBox = new TextBox().setValidationPattern(Pattern.compile("[0-9]+"));

        dialogPanel.addComponent(stockCountBox);

        // Add buttons
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        Button okButton = new Button("OK", () -> {
            try {
                int stockCount = Integer.parseInt(stockCountBox.getText());
                if (stockCount <= 0) {
                    showError("Error", "Number of stocks must be positive.");
                    return;
                }

                dialog.close();
                catalogCountDialog(stockCount);
            } catch (NumberFormatException e) {
                showError("Error", "Please enter a valid number.");
            }
        });

        Button cancelButton = new Button("Cancel", dialog::close);
        buttonPanel.addComponent(okButton);
        buttonPanel.addComponent(cancelButton);

        dialogPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        dialogPanel.addComponent(buttonPanel);

        dialog.setComponent(dialogPanel);
        textGUI.addWindowAndWait(dialog);
    }

    private void catalogCountDialog(int stockCount) {
        // Create dialog
        BasicWindow dialog = new BasicWindow("Set Catalog Count");
        dialog.setHints(Arrays.asList(Window.Hint.CENTERED, Window.Hint.MODAL));

        Panel dialogPanel = new Panel(new GridLayout(2));
        TextBox[] catalogBoxes = new TextBox[stockCount];

        for (int i = 0; i < stockCount; i++) {
            dialogPanel.addComponent(new Label("Catalogs in Stock " + (i + 1) + ": "));
            catalogBoxes[i] = new TextBox().setValidationPattern(Pattern.compile("[0-9]+"));
            dialogPanel.addComponent(catalogBoxes[i]);
        }

        // Add buttons
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        Button okButton = new Button("OK", () -> {
            try {
                int[] catalogCounts = new int[stockCount];
                for (int i = 0; i < stockCount; i++) {
                    catalogCounts[i] = Integer.parseInt(catalogBoxes[i].getText());
                    if (catalogCounts[i] <= 0) {
                        showError("Error", "Number of catalogs must be positive.");
                        return;
                    }
                }

                // Here you would call model.setupStock(stockCount, catalogCounts);
                showInfo("Success", "Stock setup completed successfully!");
                dialog.close();
            } catch (NumberFormatException e) {
                showError("Error", "Please enter valid numbers.");
            }
        });

        Button cancelButton = new Button("Cancel", dialog::close);
        buttonPanel.addComponent(okButton);
        buttonPanel.addComponent(cancelButton);

        dialogPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        dialogPanel.addComponent(buttonPanel);

        dialog.setComponent(dialogPanel);
        textGUI.addWindowAndWait(dialog);
    }

    private void insertProductDialog() {
        // Create dialog
        BasicWindow dialog = new BasicWindow("Insert Product");
        dialog.setHints(Arrays.asList(Window.Hint.CENTERED, Window.Hint.MODAL));

        Panel dialogPanel = new Panel(new GridLayout(2));

        // Stock number
        dialogPanel.addComponent(new Label("Stock Number: "));
        TextBox stockNumberBox = new TextBox().setValidationPattern(Pattern.compile("[0-9]+"));
        dialogPanel.addComponent(stockNumberBox);

        // Catalog number
        dialogPanel.addComponent(new Label("Catalog Number: "));
        TextBox catalogNumberBox = new TextBox().setValidationPattern(Pattern.compile("[0-9]+"));
        dialogPanel.addComponent(catalogNumberBox);

        // Product name
        dialogPanel.addComponent(new Label("Product Name: "));
        TextBox nameBox = new TextBox();
        dialogPanel.addComponent(nameBox);

        // Quantity
        dialogPanel.addComponent(new Label("Quantity: "));
        TextBox quantityBox = new TextBox().setValidationPattern(Pattern.compile("[0-9]+"));
        dialogPanel.addComponent(quantityBox);

        // Price
        dialogPanel.addComponent(new Label("Price: "));
        TextBox priceBox = new TextBox().setValidationPattern(Pattern.compile("[0-9]+(\\.[0-9]+)?"));
        dialogPanel.addComponent(priceBox);

        // Add buttons
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        Button okButton = new Button("OK", () -> {
            try {
                int stockNum = Integer.parseInt(stockNumberBox.getText()) - 1;
                int catNum = Integer.parseInt(catalogNumberBox.getText()) - 1;
                String name = nameBox.getText();
                int qty = Integer.parseInt(quantityBox.getText());
                double price = Double.parseDouble(priceBox.getText());

                if (name.trim().isEmpty()) {
                    showError("Error", "Product name cannot be empty.");
                    return;
                }

                if (qty < 0) {
                    showError("Error", "Quantity cannot be negative.");
                    return;
                }

                if (price < 0) {
                    showError("Error", "Price cannot be negative.");
                    return;
                }

                // Here you would call:
                // Product product = new Product(name, qty, price);
                // boolean success = model.insertProduct(stockNum, catNum, product);

                showInfo("Success", "Product has been inserted.");
                dialog.close();
            } catch (NumberFormatException e) {
                showError("Error", "Please enter valid numbers.");
            }
        });

        Button cancelButton = new Button("Cancel", dialog::close);
        buttonPanel.addComponent(okButton);
        buttonPanel.addComponent(cancelButton);

        dialogPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        dialogPanel.addComponent(buttonPanel);

        dialog.setComponent(dialogPanel);
        textGUI.addWindowAndWait(dialog);
    }

    // Helper methods
    private void showError(String title, String message) {
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle(title)
                .setText(message)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);
    }

    private void showInfo(String title, String message) {
        MessageDialogButton result = new MessageDialogBuilder()
                .setTitle(title)
                .setText(message)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);
    }

    // Method to display stock status
    public void displayStockStatus(Product[][] stock) {
        if (stock == null) {
            showError("Error", "Stock not initialized.");
            return;
        }

        // Create a new window for displaying stock status
        BasicWindow stockWindow = new BasicWindow("Stock Status");
        stockWindow.setHints(Arrays.asList(Window.Hint.CENTERED, Window.Hint.MODAL));

        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        for (int i = 0; i < stock.length; i++) {
            Label stockLabel = new Label("Stock [" + (i + 1) + "]:")
                    .addStyle(SGR.BOLD)
                    .setForegroundColor(TextColor.ANSI.BLUE);
            mainPanel.addComponent(stockLabel);

            for (int j = 0; j < stock[i].length; j++) {
                String productInfo = stock[i][j] == null ? "EMPTY" : stock[i][j].toString();
                Label catalogLabel = new Label("  Catalog [" + (j + 1) + "]: " + productInfo);
                mainPanel.addComponent(catalogLabel);
            }

            mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        }

        // Add OK button
        Button okButton = new Button("OK", stockWindow::close);
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(okButton);
        mainPanel.addComponent(buttonPanel);

        stockWindow.setComponent(mainPanel);
        textGUI.addWindowAndWait(stockWindow);
    }
}
