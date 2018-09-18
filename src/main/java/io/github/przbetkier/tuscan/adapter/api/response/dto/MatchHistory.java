package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.time.LocalDateTime;

public class MatchHistory {

    private final String matchId;
    private final LocalDateTime date;
    private final Integer elo;
    private final Integer eloGain;

    public MatchHistory(String matchId, LocalDateTime date, Integer elo, Integer eloGain) {
        this.matchId = matchId;
        this.date = date;
        this.elo = elo;
        this.eloGain = eloGain;
    }

    public String getMatchId() {
        return matchId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Integer getElo() {
        return elo;
    }

    public Integer getEloGain() {
        return eloGain;
    }
}
