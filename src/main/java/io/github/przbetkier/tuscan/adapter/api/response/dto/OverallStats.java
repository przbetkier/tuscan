package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.math.BigDecimal;

public class OverallStats {

    private final BigDecimal headshotPercentage;
    private final BigDecimal kdRatio;
    private final Integer matches;
    private final Integer winPercentage;
    private final Performance performance;
    private final Integer currentWinStreak;
    private final Integer longestWinStreak;

    public OverallStats(BigDecimal headshotPercentage, BigDecimal kdRatio, Integer matches, Integer winPercentage,
                        Performance performance, Integer currentWinStreak, Integer longestWinStreak) {
        this.headshotPercentage = headshotPercentage;
        this.kdRatio = kdRatio;
        this.matches = matches;
        this.winPercentage = winPercentage;
        this.performance = performance;
        this.currentWinStreak = currentWinStreak;
        this.longestWinStreak = longestWinStreak;
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

    public Performance getPerformance() {
        return performance;
    }

    public Integer getCurrentWinStreak() {
        return currentWinStreak;
    }

    public Integer getLongestWinStreak() {
        return longestWinStreak;
    }
}
