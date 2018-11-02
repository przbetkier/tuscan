package io.github.przbetkier.tuscan.client.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchHistoryDto {

    private String elo;
    private String matchId;
    private long date;
    private String mode;
    private String kdRatio;
    private String hsPercentage;

    @JsonCreator
    public MatchHistoryDto(@JsonProperty("elo") String elo,
            @JsonProperty("matchId") String matchId,
            @JsonProperty("date") long date,
            @JsonProperty("gameMode") String mode,
            @JsonProperty("c2") String kdRatio,
            @JsonProperty("c4") String hsPercentage) {
        this.elo = elo;
        this.matchId = matchId;
        this.date = date;
        this.mode = mode;
        this.kdRatio = kdRatio;
        this.hsPercentage = hsPercentage;
    }

    public String getElo() {
        return elo;
    }

    public String getMatchId() {
        return matchId;
    }

    public long getDate() {
        return date;
    }

    public String getMode() {
        return mode;
    }

    public String getKdRatio() {
        return kdRatio;
    }

    public String getHsPercentage() {
        return hsPercentage;
    }

    public boolean hasElo() {
        return getElo() != null;
    }
}