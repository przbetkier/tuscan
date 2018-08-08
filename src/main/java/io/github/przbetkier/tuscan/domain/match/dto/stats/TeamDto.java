package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeamDto {

    private String teamId;
    private TeamStatsDto teamStats;
    private List<PlayerDto> players;

    @JsonCreator
    public TeamDto(@JsonProperty("team_id") String teamId,
                   @JsonProperty("team_stats") TeamStatsDto teamStats,
                   @JsonProperty("players") List<PlayerDto> players) {
        this.teamId = teamId;
        this.teamStats = teamStats;
        this.players = players;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public TeamStatsDto getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(TeamStatsDto teamStats) {
        this.teamStats = teamStats;
    }

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }
}
