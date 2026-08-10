import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BillItem implements Serializable {
    private final GroceryItem item;
    private final int quantity;

    public BillItem(GroceryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public GroceryItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubTotal() {
        return item.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getDiscountAmount() {
        // calculate discount as (subTotal * discountPercent) / 100
        BigDecimal discountPercent = BigDecimal.valueOf(item.getDiscount());
        return getSubTotal().multiply(discountPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getNetPrice() {
        return getSubTotal().subtract(getDiscountAmount());
    }
}