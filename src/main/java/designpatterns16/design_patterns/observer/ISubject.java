package designpatterns16.design_patterns.observer;

//Subject interface for the Observer design pattern.
// Implemented by Items and Devices.
public interface ISubject {
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}
