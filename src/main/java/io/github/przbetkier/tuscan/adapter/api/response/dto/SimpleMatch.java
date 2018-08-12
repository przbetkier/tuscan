package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.time.LocalDateTime;

public class SimpleMatch {

    private final String matchId;
    private final LocalDateTime startedAt;
    private final LocalDateTime finishedAt;

    public SimpleMatch(String matchId, LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.matchId = matchId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public String getMatchId() {
        return matchId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }
}
