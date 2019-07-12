package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerPositionResponse;
import io.github.przbetkier.tuscan.client.player.FaceitPlayerClient;
import io.github.przbetkier.tuscan.client.player.PlayerHistoryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final FaceitPlayerClient faceitPlayerClient;
    private final PlayerHistoryClient playerHistoryClient;

    public PlayerService(FaceitPlayerClient faceitPlayerClient, PlayerHistoryClient playerHistoryClient) {
        this.faceitPlayerClient = faceitPlayerClient;
        this.playerHistoryClient = playerHistoryClient;
    }

    @Cacheable(value = "player_details", key = "#nickname")
    public Mono<PlayerDetailsResponse> getPlayerDetails(String nickname) {
        logger.info("Started FACEIT API request for {}", nickname);
        return faceitPlayerClient.getPlayerDetails(nickname);
    }

    @Cacheable(value = "player_csgo_stats", key = "#playerId")
    public Mono<PlayerCsgoStatsResponse> getCsgoStats(String playerId) {
        return faceitPlayerClient.getPlayerCsgoStats(playerId);
    }

    @Cacheable(value = "player_history", key = "#playerId")
    public PlayerHistoryResponse getPlayerHistory(String playerId) {
        return playerHistoryClient.getPlayerHistory(playerId);
    }

    public Mono<PlayerPositionResponse> getPlayerPosition(String playerId, String region, String country) {
        return Mono.zip(faceitPlayerClient.getPlayerPositionInRegion(playerId, region),
                        faceitPlayerClient.getPlayerPositionInCountry(playerId, region, country))
                .map(t -> PlayerPositionMapper.map(playerId, t))
                .doOnError(e -> logger.warn("Error occurred during fetching player position", e));
    }

    public Flux<PlayerCsgoStatsResponse> getMultiPlayerDetails(List<String> nicknamesList) {
        return Flux.fromIterable(nicknamesList)
                .flatMap(this::getPlayerDetails)
                .map(PlayerDetailsResponse::getPlayerId)
                .flatMap(this::getCsgoStats);
    }
}
