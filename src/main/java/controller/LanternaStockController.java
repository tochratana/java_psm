package controller;

import model.Product;
import model.StockModel;
import view.LanternaStockView;

public class LanternaStockController {
    private final StockModel model = new StockModel();
    private final LanternaStockView view = new LanternaStockView();

    public void start() {
        view.show();
        view.close();
    }

    // Example function to connect model and view
    private void setupStock(int stockCount, int[] catalogCounts) {
        model.setupStock(stockCount, catalogCounts);
        view.displayStockStatus(model.getStock());
    }

    public static void main(String[] args) {
        LanternaStockController controller = new LanternaStockController();
        controller.start();
    }
}