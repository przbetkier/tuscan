package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.dto.GameDetails;
import io.github.przbetkier.tuscan.client.player.Membership;
import io.github.przbetkier.tuscan.client.player.PlayerDetails;

public class PlayerDetailsMapper {

    private PlayerDetailsMapper() {
    }

    public static PlayerDetailsResponse mapToPlayerDetailsResponse(PlayerDetails details) {
        return new PlayerDetailsResponse(
                details.getPlayerId(),
                details.getNickname(),
                mapToCsgoGameDetails(details),
                details.getAvatarUrl(),
                details.getCountry(),
                details.getMemberships().stream().findFirst().map(m -> Membership.valueOf(m.toUpperCase())).orElse(null));
    }

    private static GameDetails mapToCsgoGameDetails(PlayerDetails details) {
        if (details.hasCsgoGame()) {
            return new GameDetails(
                    details.getGames().getCsgo().getFaceitElo(),
                    details.getGames().getCsgo().getLevel(),
                    details.getGames().getCsgo().getRegion(),
                    details.getGames().getCsgo().getSteamId());
        } else {
            return null;
        }
    }
}
