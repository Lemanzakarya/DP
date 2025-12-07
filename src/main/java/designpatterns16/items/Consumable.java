package designpatterns16.items;

import java.lang.reflect.Constructor;

import designpatterns16.design_patterns.observer.ISubject;

//Represents items that are consumed upon use.
//(e.g., bandages, gloves, syringes, medicine).
// Being observed for alarms when nearing expiration/out of stock.
public abstract class Consumable extends Item{
    protected String type;
    protected Double pricePerUnit;

    Consumable(String name, String type, Double pricePerUnit){
        super(name);
        this.type = type;
        this.pricePerUnit = pricePerUnit;
    }
    public String getName() {
        return name;
    }
    public Double getPricePerUnit() {
        return pricePerUnit;
    }
    public String getType() {
        return type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
    public void setType(String type) {
        this.type = type;
    }
}
