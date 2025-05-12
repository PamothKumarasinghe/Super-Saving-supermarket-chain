
import java.io.*;
import java.util.*;

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
    private String manufacturer;
    private String pDate;
    private String eDate;

    public GroceryItem(String itemCode, String itemName, double itemPrice,String manufacturer, String pDate, String eDate) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.manufacturer = manufacturer;
        this.pDate = pDate;
        this.eDate = eDate;
    }
    public double getItemPrice(int itemQuantity) {
        return itemPrice * itemQuantity;
    }
    
}
class POS {
    static final HashMap<String, GroceryItem> cart = new HashMap<>();
    Scanner sc = new Scanner(System.in);
    
    static {
        try {
            File file = new File("grocery_item.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine(); //read the first line in the file
                
            while (line != null) {
                String[] arr = line.split(",");
                String a = arr[0];
                String b = arr[1];
                double c = Double.parseDouble(arr[2]);
                String d = arr[3];
                String e = arr[4];
                String f = arr[5];

                cart.put(a, new GroceryItem(a, b, c, d, e, f));

            }
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
    public void getItemDetails() {
        System.out.println("Enter the item code:");
        String itemCode = sc.nextLine();



    }
    
}
public class SSS {
    public static void main(String[] args) {

    }
}
