package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatchStatsDto {

    private List<MatchFullDetailsDto> matchFullDetails;

    @JsonCreator
    public MatchStatsDto(@JsonProperty("rounds") List<MatchFullDetailsDto> matchFullDetails) {
        this.matchFullDetails = matchFullDetails;
    }

    public List<MatchFullDetailsDto> getMatchFullDetails() {
        return matchFullDetails;
    }
}
