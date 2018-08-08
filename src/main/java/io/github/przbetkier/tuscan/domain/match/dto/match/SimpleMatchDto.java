package io.github.przbetkier.tuscan.domain.match.dto.match;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleMatchDto {

    private String matchId;
    private Integer startedAt;
    private Integer finishedAt;

    @JsonCreator
    public SimpleMatchDto(@JsonProperty("match_id") String matchId,
                          @JsonProperty("started_at") Integer startedAt,
                          @JsonProperty("finished_at") Integer finishedAt) {
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

    public Integer getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Integer startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Integer finishedAt) {
        this.finishedAt = finishedAt;
    }
}
