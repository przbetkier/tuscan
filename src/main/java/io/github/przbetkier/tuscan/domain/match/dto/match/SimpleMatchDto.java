package io.github.przbetkier.tuscan.domain.match.dto.match;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleMatchDto {

    private String matchId;
    private long startedAt;
    private long finishedAt;

    @JsonCreator
    public SimpleMatchDto(@JsonProperty("match_id") String matchId,
                          @JsonProperty("started_at") long startedAt,
                          @JsonProperty("finished_at") long finishedAt) {
        this.matchId = matchId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Integer startedAt) {
        this.startedAt = startedAt;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Integer finishedAt) {
        this.finishedAt = finishedAt;
    }
}
