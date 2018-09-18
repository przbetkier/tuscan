package io.github.przbetkier.tuscan.domain.player.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapStatistics {

    private String matches;
    private String kdRatio;
    private String winPercentage;

    @JsonCreator
    public MapStatistics(@JsonProperty("Matches") String matches,
                         @JsonProperty("Average K/D Ratio") String kdRatio,
                         @JsonProperty("Win Rate %") String winPercentage) {
        this.matches = matches;
        this.kdRatio = kdRatio;
        this.winPercentage = winPercentage;
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

    public String getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(String winPercentage) {
        this.winPercentage = winPercentage;
    }
}
