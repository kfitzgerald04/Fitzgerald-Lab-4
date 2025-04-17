// CADataModel.java

package org.climatechange.agriculture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CADataModel implements DataSubject {
    private List<CAENTRY> allData;
    private List<CAENTRY> filteredData;
    private List<DataObserver> observers;

    public CADataModel(List<CAENTRY> data) {
        this.allData = data;
        this.filteredData = new ArrayList<>(data);
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(DataObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(DataObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (DataObserver observer : observers) {
            observer.update();
        }
    }

    public List<CAENTRY> getAllData() {
        return allData;
    }

    public List<CAENTRY> getFilteredData() {
        return filteredData;
    }

    public void setFilteredData(List<CAENTRY> filteredData) {
        this.filteredData = filteredData;
        notifyObservers();
    }

    // filter methods
    public void applyFilters(String cropType, String country, String year) {
        List<CAENTRY> newFilteredData = allData.stream()
                .filter(e -> cropType.equals("All") || e.getCropType().equals(cropType))
                .filter(e -> country.equals("All") || e.getCountry().equals(country))
                .filter(e -> year.equals("All") || String.valueOf(e.getYear()).equals(year))
                .collect(Collectors.toList());

        setFilteredData(newFilteredData);
    }

    public void resetFilters() {
        setFilteredData(new ArrayList<>(allData));
    }
}