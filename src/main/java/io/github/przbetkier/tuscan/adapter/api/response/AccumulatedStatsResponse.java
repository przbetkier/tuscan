package io.github.przbetkier.tuscan.adapter.api.response;

import java.math.BigDecimal;

public class AccumulatedStatsResponse {

    private final String csgoMap;
    private final BigDecimal avgKdRatio;
    private final Integer matches;
    private final Integer wins;
    private final Integer winRate;

    public AccumulatedStatsResponse(String csgoMap, BigDecimal avgKdRatio, Integer matches, Integer wins,
                                    Integer winRate) {
        this.csgoMap = csgoMap;
        this.avgKdRatio = avgKdRatio;
        this.matches = matches;
        this.wins = wins;
        this.winRate = winRate;
    }

    public String getCsgoMap() {
        return csgoMap;
    }

    public BigDecimal getAvgKdRatio() {
        return avgKdRatio;
    }

    public Integer getMatches() {
        return matches;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getWinRate() {
        return winRate;
    }
}
