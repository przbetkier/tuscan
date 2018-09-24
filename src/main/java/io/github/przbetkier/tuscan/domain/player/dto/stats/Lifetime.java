package io.github.przbetkier.tuscan.domain.player.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Lifetime {

    private String headshotPercentage;
    private String matches;
    private String kdRatio;
    private String winRate;
    private Integer currentWinStreak;
    private Integer longestWinStreak;

    @JsonCreator
    public Lifetime(@JsonProperty("Average Headshots %") String headshotPercentage,
                    @JsonProperty("Matches") String matches,
                    @JsonProperty("Average K/D Ratio") String kdRatio,
                    @JsonProperty("Win Rate %") String winRate,
                    @JsonProperty("Current Win Streak") Integer currentWinStreak,
                    @JsonProperty("Longest Win Streak") Integer longestWinStreak) {
        this.headshotPercentage = headshotPercentage;
        this.matches = matches;
        this.kdRatio = kdRatio;
        this.winRate = winRate;
        this.currentWinStreak = currentWinStreak;
        this.longestWinStreak = longestWinStreak;
    }

    public String getHeadshotPercentage() {
        return headshotPercentage;
    }

    public String getMatches() {
        return matches;
    }

    public String getKdRatio() {
        return kdRatio;
    }

    public String getWinRate() {
        return winRate;
    }

    public Integer getCurrentWinStreak() {
        return currentWinStreak;
    }

    public Integer getLongestWinStreak() {
        return longestWinStreak;
    }
}
