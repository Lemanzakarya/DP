package designpatterns16.Stockmonitor;

import designpatterns16.design_patterns.observer.IObserver;
import designpatterns16.Stockmonitor.StockItem;
import java.util.Date;

public class ExpirationAlarmObserver implements IObserver {

    private int thresholdDays;     // alarm threshold in days

    public ExpirationAlarmObserver(int thresholdDays) {
        this.thresholdDays = thresholdDays;
    }

    @Override
    public void update(StockItem stockItem) {
        Date exp = stockItem.getExpirationDate();
        if (exp == null) return; // No expiration date set

        long millisLeft = exp.getTime() - System.currentTimeMillis();
        long daysLeft = millisLeft / (1000 * 60 * 60 * 24);

        if (daysLeft <= thresholdDays) {
            sendAlarm(stockItem, daysLeft);
        }
    }

    private void sendAlarm(StockItem stockItem, long daysLeft) {
        System.out.println(
            "[EXPIRATION ALERT] Item: " + stockItem.getName()
            + " | Days left: " + daysLeft
            + " | Expiration date: " + stockItem.getExpirationDate()
        );
    }
}
