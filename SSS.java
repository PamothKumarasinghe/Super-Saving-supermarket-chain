class ItemCodeNotFound extends Exception {
    public ItemCodeNotFound() {}
    public ItemCodeNotFound(String message) {
        super(message);
    }
}
class GroceryItem {
    private String itemCode;
    private String itemName;
    private double itemPrice;
    private String pDate;
    private String eDate;

    public GroceryItem(String itemCode, String itemName, double itemPrice, int itemQuantiy, String pDate, String eDate) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.pDate = pDate;
        this.eDate = eDate;
    }
    public String getItemCode() {
        return itemCode;
    }
    public double getItemPrice(int itemQuantity) {
        return itemPrice * itemQuantity;
    }
    
}
class POS {

    startic 
    public GroceryItem getItemDetails() {

    }
}
public class SSS {
    public static void main(String[] args) {

    }
}
