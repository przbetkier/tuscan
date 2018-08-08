package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatchFullDetailsDto {

    private String matchId;
    private RoundStatsDto roundStatsDto;
    private List<TeamDto> teams;

    @JsonCreator
    public MatchFullDetailsDto(@JsonProperty("match_id") String matchId,
                               @JsonProperty("round_stats") RoundStatsDto roundStatsDto,
                               @JsonProperty("teams") List<TeamDto> teams) {
        this.matchId = matchId;
        this.roundStatsDto = roundStatsDto;
        this.teams = teams;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public RoundStatsDto getRoundStatsDto() {
        return roundStatsDto;
    }

    public void setRoundStatsDto(RoundStatsDto roundStatsDto) {
        this.roundStatsDto = roundStatsDto;
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDto> teams) {
        this.teams = teams;
    }
}
