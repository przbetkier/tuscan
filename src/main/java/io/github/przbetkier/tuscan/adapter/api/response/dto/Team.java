package io.github.przbetkier.tuscan.adapter.api.response.dto;

import java.util.Set;

public class Team {

    private final String teamId;
    private final String teamName;
    private final Set<Player> players;

    public Team(String teamId, String teamName, Set<Player> players) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.players = players;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
