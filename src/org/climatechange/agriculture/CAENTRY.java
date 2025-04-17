
/**
 * Kendra Fitzgerald
 * The dataset I chose is called Climate Change Agriculture
 * from Kaggle.com. This particular class represents a single
 * entry in that dataset. It encapsulates all data points for
 * a specific agricultural measurement.
 *
 */
package org.climatechange.agriculture;

public class CAENTRY {
    // attributes based on my dataset
    private int year;
    private String country;
    private String region;
    private String cropType;
    private double averageTemperature;
    private double totalPrecipitation;
    private double co2Emissions;
    private double cropYield;
    private int extremeWeatherEvents;
    private double irrigation;

    /**
     * ClimateAgricultureEntry data meaning
     *
     * @param year Year of the agricultural data
     * @param country Country where the data was collected
     * @param region Specific region within the country
     * @param cropType Type of crop measured
     * @param averageTemperature Average temperature for the period
     * @param totalPrecipitation Total precipitation
     * @param co2Emissions CO2 emissions
     * @param cropYield Crop yield in tons per hectare
     * @param extremeWeatherEvents Number of extreme weather events
     * @param irrigation Irrigation amount
     */

    public CAENTRY(int year, String country, String region,
                   String cropType, double averageTemperature,
                   double totalPrecipitation, double co2Emissions,
                   double cropYield, int extremeWeatherEvents,
                   double irrigation)
    {
        this.year = year;
        this.country = country;
        this.region = region;
        this.cropType = cropType;
        this.averageTemperature = averageTemperature;
        this.totalPrecipitation = totalPrecipitation;
        this.co2Emissions = co2Emissions;
        this.cropYield = cropYield;
        this.extremeWeatherEvents = extremeWeatherEvents;
        this.irrigation = irrigation;
    }

    // toString method for printing
    @Override
    public String toString() {
        return String.format(
                "Year: %d, Country: %s, Region: %s, Crop: %s, " +
                        "Temp: %.2f°C, Precipitation: %.2f mm, CO2: %.2f, " +
                        "Crop Yield: %.2f tons/ha, Extreme Weather: %d, Irrigation: %.2f",
                year, country, region, cropType, averageTemperature,
                totalPrecipitation, co2Emissions, cropYield,
                extremeWeatherEvents, irrigation
        );
    }

    // getters for all attributes
    public int getYear() { return year; }
    public String getCountry() { return country; }
    public String getRegion() { return region; }
    public String getCropType() { return cropType; }
    public double getAverageTemperature() { return averageTemperature; }
    public double getTotalPrecipitation() { return totalPrecipitation; }
    public double getCo2Emissions() { return co2Emissions; }
    public double getCropYield() { return cropYield; }
    public int getExtremeWeatherEvents() { return extremeWeatherEvents; }
    public double getIrrigation() { return irrigation; }
}