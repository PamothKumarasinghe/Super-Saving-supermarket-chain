import java.util.Map;

public interface ProductRepositary {
    Map<String, GroceryItem> loadProducts();

    GroceryItem findByCode(String itemCode);
}