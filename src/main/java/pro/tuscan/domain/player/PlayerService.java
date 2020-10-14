package pro.tuscan.domain.player;

import kotlin.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pro.tuscan.adapter.api.PlayerDetailsResponse;
import pro.tuscan.adapter.api.response.DetailedPlayerCsgoStatsResponse;
import pro.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import pro.tuscan.adapter.api.response.PlayerHistoryResponse;
import pro.tuscan.adapter.api.response.PlayerPositionResponse;
import pro.tuscan.client.player.BanInfoResponse;
import pro.tuscan.client.player.FaceitPlayerClient;
import pro.tuscan.client.player.PlayerBanClient;
import pro.tuscan.client.player.PlayerHistoryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final FaceitPlayerClient faceitPlayerClient;
    private final PlayerHistoryClient playerHistoryClient;
    private final PlayerBanClient playerBanClient;

    public PlayerService(FaceitPlayerClient faceitPlayerClient, PlayerHistoryClient playerHistoryClient,
                         PlayerBanClient playerBanClient) {
        this.faceitPlayerClient = faceitPlayerClient;
        this.playerHistoryClient = playerHistoryClient;
        this.playerBanClient = playerBanClient;
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

    public Mono<BanInfoResponse> getPlayerBanInfo(String playerId) {
        return playerBanClient.getPlayerBanInfo(playerId);
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
