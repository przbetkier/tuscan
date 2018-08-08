package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoundStatsDto {

    private String map;
    private String roundsCount;
    private String score;
    private String winnerTeamId;

    @JsonCreator
    public RoundStatsDto(@JsonProperty("Map") String map,
                         @JsonProperty("Rounds") String roundsCount,
                         @JsonProperty("Score") String score,
                         @JsonProperty("Winner") String winnerTeamId) {
        this.map = map;
        this.roundsCount = roundsCount;
        this.score = score;
        this.winnerTeamId = winnerTeamId;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getRoundsCount() {
        return roundsCount;
    }

    public void setRoundsCount(String roundsCount) {
        this.roundsCount = roundsCount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(String winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }
}
