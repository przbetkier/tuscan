package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class SimpleMatch {

    private final String matchId;
    private final Integer startedAt; // TODO: Map it to LocalDateTime and replace
    private final Integer finishedAt; // TODO: Map it to LocalDateTime and replace

    public SimpleMatch(String matchId, Integer startedAt, Integer finishedAt) {
        this.matchId = matchId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public String getMatchId() {
        return matchId;
    }

    public Integer getStartedAt() {
        return startedAt;
    }

    public Integer getFinishedAt() {
        return finishedAt;
    }
}
