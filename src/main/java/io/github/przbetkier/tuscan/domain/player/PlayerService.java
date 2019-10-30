package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.DetailedPlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerPositionResponse;
import io.github.przbetkier.tuscan.client.player.FaceitPlayerClient;
import io.github.przbetkier.tuscan.client.player.PlayerHistoryClient;
import kotlin.Pair;
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

    @Cacheable(value = "playerDetails", key = "{#nickname}")
    public Mono<PlayerDetailsResponse> getPlayerDetails(String nickname) {
        return faceitPlayerClient.getPlayerDetails(nickname);
    }

    @Cacheable(value = "playerCsgoStats", key = "{#playerId}")
    public Mono<PlayerCsgoStatsResponse> getCsgoStats(String playerId) {
        return faceitPlayerClient.getPlayerCsgoStats(playerId);
    }

    @Cacheable(value = "playerHistory", key = "{#playerId}")
    public Mono<PlayerHistoryResponse> getPlayerHistory(String playerId) {
        return playerHistoryClient.getPlayerHistory(playerId);
    }

    @Cacheable(value = "playerPosition", key = "{#playerId, #region, #country}")
    public Mono<PlayerPositionResponse> getPlayerPosition(String playerId, String region, String country) {
        return Mono.zip(faceitPlayerClient.getPlayerPositionInRegion(playerId, region),
                        faceitPlayerClient.getPlayerPositionInCountry(playerId, region, country))
                .map(t -> PlayerPositionMapper.map(playerId, t))
                .doOnError(e -> logger.warn("Error occurred during fetching player position", e));
    }

    public Flux<DetailedPlayerCsgoStatsResponse> getMultiPlayerDetails(List<String> nicknamesList) {
        return Flux.fromIterable(nicknamesList)
                .flatMap(nickname -> getPlayerDetails(nickname).onErrorResume(e -> Mono.empty()))
                // Combine id with nickname
                .map(details -> new Pair<>(details.getPlayerId(), details.getNickname()))
                // Fetch CSGO stats using ID and pass the nickname further
                .flatMap(p -> Mono.zip(getCsgoStats(p.component1()).onErrorResume(e -> Mono.empty()),
                                       Mono.just(p.component2()))
                        // map to extended response - plugin HAS to know relation STATS<=>nickname!
                        .map(t -> DetailedPlayerCsgoStatsResponse.fromPlayerCsgoStatsResponse(t.getT1(), t.getT2())));
    }
}
