import controller.StockController;

public class App {
    public static void main(String[] args) {
        new StockController().start();
    }
}

// for use Lanterna ui, but it have some error that I don't have to complete yet! :

//public class App {
//    public static void main(String[] args) {
//        LanternaStockController controller = new LanternaStockController();
//        controller.start();
//    }
//}