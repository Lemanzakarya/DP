package designpatterns16.design_patterns.composite;

import designpatterns16.items.Item;
import java.util.ArrayList;
import java.util.List;

//Composite class for Composite pattern
//Represents a category that can contain subcategories and products
public class ProductCategory implements Category {
    private String name;
    private List<Category> children;

    public ProductCategory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void add(Category category) {
        children.add(category);
    }

    @Override
    public void remove(Category category) {
        children.remove(category);
    }

    @Override
    public List<Category> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        for (Category child : children) {
            allItems.addAll(child.getAllItems());
        }
        return allItems;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Category: " + name);
        for (Category child : children) {
            child.display(indent + "  ");
        }
    }

    public void display() {
        display("");
    }
}

