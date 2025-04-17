// THIS CLASS WILL CALCULATE THE TOTAL EXTREME EVENTS (STRATEGY)

package org.climatechange.agriculture;

import java.util.List;

public class TEES implements AnalysisStrategy<Integer> {
    @Override
    public Integer analyze(List<CAENTRY> data) {
        return data.stream()
                .mapToInt(CAENTRY::getExtremeWeatherEvents)
                .sum();
    }
}
