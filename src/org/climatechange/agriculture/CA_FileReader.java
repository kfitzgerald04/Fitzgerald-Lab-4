/**
 * Kendra Fitzgerald
 * This class will handle reading and parsing the data
 * from the CSV file
 */

package org.climatechange.agriculture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CA_FileReader {

    public static List<CAENTRY> readDataFromFile(String filePath) {
        List<CAENTRY> agricultureData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // read and print header line for verification
            String headerLine = br.readLine();
            System.out.println("Dataset Columns: " + headerLine);

            // read and parse data lines
            agricultureData = br.lines()
                    .map(CA_FileReader::parseDataLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return agricultureData;
    }


    // parse a string to integer or double, returning a default value if parsing fails
    private static <T> T parseValue(String value, T defaultValue, ValueParser<T> parser) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return parser.parse(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // functional interface for parsing values (using pt1 feedback)
    @FunctionalInterface
    private interface ValueParser<T> {
        T parse(String value) throws NumberFormatException;
    }

    // parses a single line of CSV data into a CAEntry object
    private static CAENTRY parseDataLine(String csvLine) {
        String[] climateDataFields = csvLine.split(",");

        // ensure the array has enough elements
        if (climateDataFields.length < 10) {
            System.err.println("Warning: Incomplete climate data record: " + csvLine);
            return null;
        }

        // data fields from the CSV file
        String yearField = climateDataFields[0];
        String countryField = climateDataFields[1];
        String regionField = climateDataFields[2];
        String cropTypeField = climateDataFields[3];
        String temperatureField = climateDataFields[4];
        String precipitationField = climateDataFields[5];
        String co2EmissionsField = climateDataFields[6];
        String cropYieldField = climateDataFields[7];
        String extremeWeatherField = climateDataFields[8];
        String irrigationField = climateDataFields[9];

        // create and return a new CAEntry object
        return new CAENTRY(
                parseValue(yearField, 0, Integer::parseInt),                     // Year
                countryField,                                                                 // Country
                regionField,                                                                 // Region
                cropTypeField,                                                             // Crop Type
                parseValue(temperatureField, 0.0, Double::parseDouble),      // Average Temperature
                parseValue(precipitationField, 0.0, Double::parseDouble),    // Total Precipitation
                parseValue(co2EmissionsField, 0.0, Double::parseDouble),     // CO2 Emissions
                parseValue(cropYieldField, 0.0, Double::parseDouble),        // Crop Yield
                parseValue(extremeWeatherField, 0, Integer::parseInt),       // Extreme Weather Events
                parseValue(irrigationField, 0.0, Double::parseDouble)        // Irrigation
        );
    }
}