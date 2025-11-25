package designpatterns16.design_patterns.observer;

import designpatterns16.items.Consumable;

//Observer interface for the Observer design pattern.
//All alert systems implement this.
public interface IObserver {
    void update(Consumable consumable);
}