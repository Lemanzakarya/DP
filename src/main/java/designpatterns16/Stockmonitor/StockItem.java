package designpatterns16.Stockmonitor;

import designpatterns16.design_patterns.observer.IObserver;
import designpatterns16.design_patterns.observer.ISubject;
import designpatterns16.items.Consumable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockItem implements ISubject {

    private Consumable product;
    private int quantity;
    private Date expirationDate;
    private List<IObserver> observers = new ArrayList<>();

    public StockItem(Consumable product, int quantity, Date expirationDate) {
        this.product = product;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    // ---------- ISubject IMPLEMENTATION ----------
    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }

    // ---------- STOCK MANAGEMENT ----------
    public void reduceStock(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Insufficient stock!");
        }
        this.quantity -= amount;

        // Trigger alarms when threshold or critical level reached
        notifyObservers();
    }

    public void increaseStock(int amount) {
        this.quantity += amount;
        notifyObservers();
    }

    // ---------- GETTERS ----------
    public Consumable getProduct() {
        return product;
    }

    public int getStock() {
        return quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getType() {
        return product.getType();
    }

    public String getName() {
        return product.getName();
    }

    public double getTotalValue() {
        return product.getPricePerUnit() * quantity;
    }

    public boolean isExpired() {
        return expirationDate != null && expirationDate.before(new Date());
    }

    // ---------- UTILS ----------
    @Override
    public String toString() {
        return "StockItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
