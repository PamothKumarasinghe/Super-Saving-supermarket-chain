import java.io.Serializable;
import java.math.BigDecimal;

class GroceryItem implements Serializable {
    private final String itemCode;
    private final String itemName;
    private final BigDecimal itemPrice; // why BigDecimal? because "double" can have precision errors
    private final String manufacturer;
    private final String pDate;
    private final String eDate;
    private final int discount;

    public GroceryItem(String itemCode, String itemName, BigDecimal itemPrice, String manufacturer, String pDate, String eDate, int discount) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.manufacturer = manufacturer;
        this.pDate = pDate;
        this.eDate = eDate;
        this.discount = discount;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getProductionDate() {
        return pDate;
    }

    public String getExpirationDate() {
        return eDate;
    }

    public BigDecimal getUnitPrice() {
        return itemPrice;
    }

    public int getDiscount() {
        return discount;
    }
}
