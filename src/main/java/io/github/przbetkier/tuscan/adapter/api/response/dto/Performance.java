package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class Performance {

    private final SoloPerformance bestSoloPerformance;
    private final TeamPerformance bestTeamPerformance;
    private final SoloPerformance worstSoloPerformance;
    private final TeamPerformance worstTeamPerformance;

    public Performance(SoloPerformance bestSoloPerformance,
                       TeamPerformance bestTeamPerformance,
                       SoloPerformance worstSoloPerformance,
                       TeamPerformance worstTeamPerformance) {
        this.bestSoloPerformance = bestSoloPerformance;
        this.bestTeamPerformance = bestTeamPerformance;
        this.worstSoloPerformance = worstSoloPerformance;
        this.worstTeamPerformance = worstTeamPerformance;
    }

    public SoloPerformance getBestSoloPerformance() {
        return bestSoloPerformance;
    }

    public TeamPerformance getBestTeamPerformance() {
        return bestTeamPerformance;
    }

    public SoloPerformance getWorstSoloPerformance() {
        return worstSoloPerformance;
    }

    public TeamPerformance getWorstTeamPerformance() {
        return worstTeamPerformance;
    }
}
