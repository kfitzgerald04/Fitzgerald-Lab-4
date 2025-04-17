package org.climatechange.agriculture;

public interface DataSubject {
    void addObserver(DataObserver observer);
    void removeObserver(DataObserver observer);
    void notifyObservers();
}

