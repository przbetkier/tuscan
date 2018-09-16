package io.github.przbetkier.tuscan.adapter.api.response.dto;

import io.github.przbetkier.tuscan.domain.CsgoMap;

import java.math.BigDecimal;

public class MapStats {

    private final CsgoMap csgoMap;
    private final Integer matches;
    private final BigDecimal kdRatio;
    private final Integer winPercentage;

    public MapStats(CsgoMap csgoMap, Integer matches, BigDecimal kdRatio, Integer winPercentage) {
        this.csgoMap = csgoMap;
        this.matches = matches;
        this.kdRatio = kdRatio;
        this.winPercentage = winPercentage;
    }

    public CsgoMap getCsgoMap() {
        return csgoMap;
    }

    public Integer getMatches() {
        return matches;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }

    public Integer getWinPercentage() {
        return winPercentage;
    }
}
