// THIS CLASS WILL CALCULATE THE AVERAGE CROP YIELD (STRATEGY)

package org.climatechange.agriculture;

import java.util.List;

public class ACYS implements AnalysisStrategy<Double> {
    @Override
    public Double analyze(List<CAENTRY> data) {
        return data.stream()
                .mapToDouble(CAENTRY::getCropYield)
                .average()
                .orElse(0.0);
    }
}