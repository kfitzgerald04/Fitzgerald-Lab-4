// AnalysisStrategy.java
package org.climatechange.agriculture;

import java.util.List;

public interface AnalysisStrategy<T> {
    T analyze(List<CAENTRY> data);
}