package pro.tuscan.domain.player;

import pro.tuscan.adapter.api.response.PlayerDetailsResponse;
import pro.tuscan.adapter.api.response.dto.BanInfo;
import pro.tuscan.adapter.api.response.dto.GameDetails;
import pro.tuscan.client.player.Ban;
import pro.tuscan.client.player.Membership;
import pro.tuscan.client.player.PlayerDetails;

import java.time.ZonedDateTime;
import java.util.List;

public class PlayerDetailsMapper {

    private PlayerDetailsMapper() {
    }

    public static PlayerDetailsResponse mapToPlayerDetailsResponse(PlayerDetails details) {
        return new PlayerDetailsResponse(details.getPlayerId(),
                                         details.getNickname(),
                                         mapToCsgoGameDetails(details),
                                         details.getAvatarUrl(),
                                         details.getCountry(),
                                         details.getMemberships()
                                                 .stream()
                                                 .findFirst()
                                                 .map(m -> Membership.valueOf(m.toUpperCase()))
                                                 .orElse(null),
                                         mapBanInfo(details.getBans()));
    }

    private static GameDetails mapToCsgoGameDetails(PlayerDetails details) {
        return details.hasCsgoGame() ? new GameDetails(details.getGames().getCsgo().getFaceitElo(),
                                                       details.getGames().getCsgo().getLevel(),
                                                       details.getGames().getCsgo().getRegion(),
                                                       details.getGames().getCsgo().getSteamId()) : null;
    }

    private static BanInfo mapBanInfo(List<Ban> bans) {
        boolean activeBan = bans.stream().anyMatch(ban -> ban.getStartsAt().isBefore(ZonedDateTime.now()));
        return new BanInfo(activeBan);
    }

}
