// THIS CLASS WILL CALCULATE THE AVERAGE TEMPERATURE (STRATEGY)

package org.climatechange.agriculture;

import java.util.List;

public class ATS implements AnalysisStrategy<Double> {
    @Override
    public Double analyze(List<CAENTRY> data) {
        return data.stream()
                .mapToDouble(CAENTRY::getAverageTemperature)
                .average()
                .orElse(0.0);
    }
}