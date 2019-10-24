package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.time.Instant;

public class SimpleMatch {

    private final String matchId;
    private final Instant startedAt;
    private final Instant finishedAt;

    public SimpleMatch(String matchId, Instant startedAt, Instant finishedAt) {
        this.matchId = matchId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public String getMatchId() {
        return matchId;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }
}
