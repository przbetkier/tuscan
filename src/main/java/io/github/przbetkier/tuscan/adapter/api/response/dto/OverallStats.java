package io.github.przbetkier.tuscan.adapter.api.response.dto;

import io.github.przbetkier.tuscan.domain.CsgoMap;

import java.math.BigDecimal;

public class OverallStats {

    private final BigDecimal headshotPercentage;
    private final BigDecimal kdRatio;
    private final Integer matches;
    private final Integer winPercentage;
    private final CsgoMap highestSoloPerformanceOn;
    private final CsgoMap highestTeamPerformanceOn;

    public OverallStats(BigDecimal headshotPercentage, BigDecimal kdRatio, Integer matches, Integer winPercentage,
                        CsgoMap highestSoloPerformanceOn, CsgoMap highestTeamPerformanceOn) {
        this.headshotPercentage = headshotPercentage;
        this.kdRatio = kdRatio;
        this.matches = matches;
        this.winPercentage = winPercentage;
        this.highestSoloPerformanceOn = highestSoloPerformanceOn;
        this.highestTeamPerformanceOn = highestTeamPerformanceOn;
    }

    public BigDecimal getHeadshotPercentage() {
        return headshotPercentage;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }

    public Integer getMatches() {
        return matches;
    }

    public Integer getWinPercentage() {
        return winPercentage;
    }

    public CsgoMap getHighestSoloPerformanceOn() {
        return highestSoloPerformanceOn;
    }

    public CsgoMap getHighestTeamPerformanceOn() {
        return highestTeamPerformanceOn;
    }
}
