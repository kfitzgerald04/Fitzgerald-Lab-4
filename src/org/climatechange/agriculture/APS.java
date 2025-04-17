// THIS CLASS WILL CALCULATE THE AVERAGE PRECIPITATION (STRATEGY)

package org.climatechange.agriculture;

import java.util.List;

public class APS implements AnalysisStrategy<Double> {
    @Override
    public Double analyze(List<CAENTRY> data) {
        return data.stream()
                .mapToDouble(CAENTRY::getTotalPrecipitation)
                .average()
                .orElse(0.0);
    }
}