package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final FaceitPlayerClient faceitPlayerClient;
    private final PlayerHistoryClient playerHistoryClient;

    public PlayerService(FaceitPlayerClient faceitPlayerClient,
                         PlayerHistoryClient playerHistoryClient) {
        this.faceitPlayerClient = faceitPlayerClient;
        this.playerHistoryClient = playerHistoryClient;
    }

    @Cacheable(value = "player_details", key = "#nickname")
    public PlayerDetailsResponse getPlayerDetails(String nickname) {
        return faceitPlayerClient.getPlayerDetails(nickname);
    }

    @Cacheable(value = "player_csgo_stats", key = "#playerId")
    public PlayerCsgoStatsResponse getCsgoStats(String playerId) {
        return faceitPlayerClient.getPlayerCsgoStats(playerId);
    }

    @Cacheable(value = "player_history", key = "#playerId")
    public PlayerHistoryResponse getPlayerHistory(String playerId) {
        return playerHistoryClient.getPlayerHistory(playerId);
    }
}
