package io.github.przbetkier.tuscan.domain.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchHistoryDto {

    private String elo;
    private String matchId;
    private long date;

    @JsonCreator
    public MatchHistoryDto(@JsonProperty("elo") String elo,
                           @JsonProperty("matchId") String matchId,
                           @JsonProperty("date") long date) {
        this.elo = elo;
        this.matchId = matchId;
        this.date = date;
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
}
