package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails;
import io.github.przbetkier.tuscan.domain.match.dto.player.PlayerDetails;

class PlayerDetailsMapper {

    private PlayerDetailsMapper() {
    }

    static PlayerDetailsResponse mapToPlayerDetailsResponse(PlayerDetails details) {
        return new PlayerDetailsResponse(
                details.getPlayerId(),
                details.getNickname(),
                new GameDetails(
                        details.getGames().getCsgo().getFaceitElo(),
                        details.getGames().getCsgo().getLevel()),
                details.getAvatarUrl());
    }
}
