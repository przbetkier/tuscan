package pro.tuscan.client.search;

import pro.tuscan.adapter.api.response.PlayerSearchResponse;
import pro.tuscan.adapter.api.response.PlayerSimple;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerSearchMapper {

    private PlayerSearchMapper() {
    }

    public static PlayerSearchResponse map(FaceitSearchDTO faceitSearchDTO) {
        List<PlayerSimple> players = faceitSearchDTO.getPayload()
                .getPlayers()
                .getResults()
                .stream()
                // TakeIf from kt
                .filter(PlayerSearch::hasCsgoGame)
                .map(p -> new PlayerSimple(p.getNickname(),
                                           p.getCsgoGame().getLvl(),
                                           p.getCountry(),
                                           p.getAvatarUrl(),
                                           p.isVerified()))
                .limit(3)
                .collect(Collectors.toList());
        return new PlayerSearchResponse(players);
    }
}
