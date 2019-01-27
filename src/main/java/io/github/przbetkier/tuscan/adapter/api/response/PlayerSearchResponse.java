package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerSimple;

import java.util.List;

public class PlayerSearchResponse {

    private final List<PlayerSimple> players;

    public PlayerSearchResponse(List<PlayerSimple> players) {
        this.players = players;
    }

    public List<PlayerSimple> getPlayers() {
        return players;
    }
}
