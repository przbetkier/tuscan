package io.github.przbetkier.tuscan.domain.player.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Lifetime {

    private String headshotPercentage;
    private String matches;
    private String kdRatio;
    private String winRate;

    @JsonCreator
    public Lifetime(@JsonProperty("Average Headshots %") String headshotPercentage,
                    @JsonProperty("Matches") String matches,
                    @JsonProperty("Average K/D Ratio") String kdRatio,
                    @JsonProperty("Win Rate %") String winRate) {
        this.headshotPercentage = headshotPercentage;
        this.matches = matches;
        this.kdRatio = kdRatio;
        this.winRate = winRate;
    }

    public String getHeadshotPercentage() {
        return headshotPercentage;
    }

    public void setHeadshotPercentage(String headshotPercentage) {
        this.headshotPercentage = headshotPercentage;
    }

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    public String getKdRatio() {
        return kdRatio;
    }

    public void setKdRatio(String kdRatio) {
        this.kdRatio = kdRatio;
    }

    public String getWinRate() {
        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }
}
