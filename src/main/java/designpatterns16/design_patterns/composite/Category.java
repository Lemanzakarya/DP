package designpatterns16.design_patterns.composite;

import designpatterns16.items.Item;
import java.util.List;

//Component interface for Composite pattern
//Represents a category that can contain items or subcategories
public interface Category {
    String getName();
    void add(Category category);
    void remove(Category category);
    List<Category> getChildren();
    List<Item> getAllItems();
    void display(String indent);
}

