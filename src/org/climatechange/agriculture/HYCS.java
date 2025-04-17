// THIS CLASS WILL CALCULATE THE HIGHEST YIELD FOR A COUNTRY (STRATEGY)

package org.climatechange.agriculture;

import java.util.List;

public class HYCS implements AnalysisStrategy<String> {
    @Override
    public String analyze(List<CAENTRY> data) {
        return data.stream()
                .max((a, b) -> Double.compare(a.getCropYield(), b.getCropYield()))
                .map(CAENTRY::getCountry)
                .orElse("No data");
    }
}
