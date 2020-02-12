package pro.tuscan.adapter.api.response.dto;

import pro.tuscan.domain.CsgoMap;

import java.math.BigDecimal;

public class SoloPerformance {

    private final CsgoMap map;
    private final BigDecimal kdRatio;

    public SoloPerformance(CsgoMap map, BigDecimal kdRatio) {
        this.map = map;
        this.kdRatio = kdRatio;
    }

    public CsgoMap getMap() {
        return map;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }
}
