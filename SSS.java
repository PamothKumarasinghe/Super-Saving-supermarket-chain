public class SSS {
    public static void main(String[] args) {

        ProductRepositary productRepositary = new FileProductRepositary("grocery_items.txt");
        POS pos = new POS(productRepositary);
        POSConsoleUI ui = new POSConsoleUI(pos);
        ui.start();
        // rest
        System.out.println("\nThank you for shopping with us!");
        System.out.println("===========================================================");

        System.exit(0);
    }
}