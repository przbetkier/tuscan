package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerPositionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final FaceitPlayerClient faceitPlayerClient;
    private final PlayerHistoryClient playerHistoryClient;
    private final TaskExecutor taskExecutor;

    public PlayerService(FaceitPlayerClient faceitPlayerClient,
                         PlayerHistoryClient playerHistoryClient, TaskExecutor taskExecutor) {
        this.faceitPlayerClient = faceitPlayerClient;
        this.playerHistoryClient = playerHistoryClient;
        this.taskExecutor = taskExecutor;
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

    public Mono<PlayerPositionResponse> getPlayerPosition(String playerId, String region, String country) {
        return Mono.zip(faceitPlayerClient.getPlayerPositionInRegion(playerId, region),
                faceitPlayerClient.getPlayerPositionInCountry(playerId, region, country))
                .map(t -> PlayerPositionMapper.map(playerId, t))
                .doOnError(e -> logger.warn("Error occurred during fetching player position", e))
                .subscribeOn(Schedulers.fromExecutor(taskExecutor));
    }
}
