package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.util.Set;

public class Team {

    private final String teamId;
    private final TeamStats teamStats;
    private final Set<Player> players;

    public Team(String teamId, TeamStats teamStats, Set<Player> players) {
        this.teamId = teamId;
        this.teamStats = teamStats;
        this.players = players;
    }

    public String getTeamId() {
        return teamId;
    }

    public TeamStats getTeamStats() {
        return teamStats;
    }

    public Set<Player> getPlayers() {
        return players;
    }

}