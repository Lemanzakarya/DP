package designpatterns16.items;

import designpatterns16.design_patterns.observer.ISubject;

//Represents items that are consumed upon use.
//(e.g., bandages, gloves, syringes, medicine).
// Being observed for alarms when nearing expiration/out of stock.
public class Consumable extends Item implements ISubject {
    
}
