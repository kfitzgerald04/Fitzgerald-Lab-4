// CAParser.java (UPDATE TO IMPLEMENT STRATEGY PATTERN)

package org.climatechange.agriculture;

import java.util.List;
import java.util.stream.Collectors;

public class CAParser {
    private List<CAENTRY> agricultureData;
    private AnalysisStrategy<?> currentStrategy;

    // initializing the parser with dataset
    public CAParser(List<CAENTRY> agricultureData) {
        this.agricultureData = agricultureData;
    }

    // implementing strategy pattern methods
    public void setStrategy(AnalysisStrategy<?> strategy) {
        this.currentStrategy = strategy;
    }

    public Object executeAnalysis() {
        return currentStrategy.analyze(agricultureData);
    }

    public Object executeAnalysis(List<CAENTRY> specificData) {
        return currentStrategy.analyze(specificData);
    }

    // original methods from lab 3 for backward compatibility
    public void printFirstItemDetails() {
        if (!agricultureData.isEmpty()) {
            System.out.println("First Data Entry:");
            System.out.println(agricultureData.get(0));
        }
    }

    public void printTenthItemDetails() {
        if (agricultureData.size() >= 10) {
            System.out.println("Tenth Data Entry:");
            System.out.println(agricultureData.get(9));
        }
    }

    public void printTotalEntries() {
        System.out.println("Total Number of Entries: " + agricultureData.size());
    }

    // updated methods to use strategy pattern internally
    public double calculateAverageCropYield() {
        setStrategy(new ACYS());
        return (Double) executeAnalysis();
    }

    public double calculateAverageTemperature() {
        setStrategy(new ATS());
        return (Double) executeAnalysis();
    }

    public double calculateAveragePrecipitation() {
        setStrategy(new APS());
        return (Double) executeAnalysis();
    }

    public int calculateTotalExtremeWeatherEvents() {
        setStrategy(new TEES());
        return (Integer) executeAnalysis();
    }

    public String findHighestYieldCountry() {
        setStrategy(new HYCS());
        return (String) executeAnalysis();
    }

    //  filters entries by a specific crop type
    public List<CAENTRY> filterByCropType(String cropType) {
        return agricultureData.stream()
                .filter(entry -> entry.getCropType().equalsIgnoreCase(cropType))
                .collect(Collectors.toList());
    }
}