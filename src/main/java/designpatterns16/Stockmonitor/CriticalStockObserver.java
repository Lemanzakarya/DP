package designpatterns16.Stockmonitor;

import designpatterns16.design_patterns.observer.IObserver;
import designpatterns16.Stockmonitor.StockItem;

public class CriticalStockObserver implements IObserver {

    private int threshold;   // alarm threshold
        
    public CriticalStockObserver(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void update(StockItem stockItem) {
        int current = stockItem.getStock();

        if (current <= threshold) {
            sendAlarm(stockItem, current);
        }
    }

    private void sendAlarm(StockItem stockItem, int currentStock) {
        System.out.println(
            "[CRITICAL STOCK ALERT] Item: " + stockItem.getName() +
            " | Current stock: " + currentStock +
            " | Threshold: " + threshold
        );
    }
}
