package io.github.przbetkier.tuscan.domain.match.dto.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamStatsDto {

    private String teamName;
    private String headshotAvg;

    @JsonCreator
    public TeamStatsDto(@JsonProperty("Team") String teamName,
                        @JsonProperty("Team Headshot") String headshotAvg) {
        this.teamName = teamName;
        this.headshotAvg = headshotAvg;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getHeadshotAvg() {
        return headshotAvg;
    }

    public void setHeadshotAvg(String headshotAvg) {
        this.headshotAvg = headshotAvg;
    }
}
