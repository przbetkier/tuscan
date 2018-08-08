package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class TeamStats {

    private final String teamName;
    private final Double headshotAvg;

    public TeamStats(String teamName, Double headshotAvg) {
        this.teamName = teamName;
        this.headshotAvg = headshotAvg;
    }

    public String getTeamName() {
        return teamName;
    }

    public Double getHeadshotAvg() {
        return headshotAvg;
    }
}