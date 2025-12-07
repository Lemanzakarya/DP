package designpatterns16.Stockmonitor;

import designpatterns16.design_patterns.composite.Category;
import designpatterns16.design_patterns.composite.ProductCategory;
import designpatterns16.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
    private List<StockItem> stockItems = new ArrayList<>();
    private ProductCategory rootCategory;

    public Inventory() {
        this.rootCategory = new ProductCategory("All Products");
    }

    public void addStock(StockItem item) {
        stockItems.add(item);
    }

    public void removeStock(StockItem item) {
        if(!stockItems.contains(item)) {
            throw new IllegalArgumentException("Item not found in inventory!");
        }
        stockItems.remove(item);
    }

    public List<StockItem> getStockByProduct(String name) {
        return stockItems.stream()
                .filter(s -> s.getProduct().getName().equals(name))
                .collect(Collectors.toList());
    }

    // Composite pattern integration
    public ProductCategory getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(ProductCategory rootCategory) {
        this.rootCategory = rootCategory;
    }

    public List<StockItem> getStockByCategory(String categoryName) {
        List<StockItem> result = new ArrayList<>();
        Category category = findCategoryByName(rootCategory, categoryName);
        if (category != null) {
            List<Item> items = category.getAllItems();
            for (Item item : items) {
                List<StockItem> stockForItem = stockItems.stream()
                        .filter(s -> s.getProduct().equals(item))
                        .collect(Collectors.toList());
                result.addAll(stockForItem);
            }
        }
        return result;
    }

    private Category findCategoryByName(Category category, String name) {
        if (category.getName().equals(name)) {
            return category;
        }
        for (Category child : category.getChildren()) {
            Category found = findCategoryByName(child, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public void displayCategoryStructure() {
        System.out.println("=== Category Structure ===");
        rootCategory.display();
    }

    public List<StockItem> getAllStockItems() {
        return new ArrayList<>(stockItems);
    }
}

