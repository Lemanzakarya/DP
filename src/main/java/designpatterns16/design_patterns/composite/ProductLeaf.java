package designpatterns16.design_patterns.composite;

import designpatterns16.items.Item;
import java.util.ArrayList;
import java.util.List;

//Leaf class for Composite pattern
//Represents a single product item in a category
public class ProductLeaf implements Category {
    private Item item;

    public ProductLeaf(Item item) {
        this.item = item;
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public void add(Category category) {
        throw new UnsupportedOperationException("Cannot add to a leaf node");
    }

    @Override
    public void remove(Category category) {
        throw new UnsupportedOperationException("Cannot remove from a leaf node");
    }

    @Override
    public List<Category> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        items.add(item);
        return items;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Product: " + item.getName() + " (" + item.getClass().getSimpleName() + ")");
    }

    public Item getItem() {
        return item;
    }
}

