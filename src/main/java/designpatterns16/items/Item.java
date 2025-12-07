package designpatterns16.items;

//Represents ANY single physical or digital item
public abstract class Item {
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
