package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.domain.player.dto.Position;
import io.github.przbetkier.tuscan.domain.player.dto.stats.PlayerStats;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import io.github.przbetkier.tuscan.exception.FaceitServerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class FaceitPlayerClient {

    private final WebClient faceitClient;
    private final TaskExecutor taskExecutor;

    public FaceitPlayerClient(@Qualifier("faceitClient") WebClient faceitClient, TaskExecutor taskExecutor) {
        this.faceitClient = faceitClient;
        this.taskExecutor = taskExecutor;
    }

    public PlayerDetailsResponse getPlayerDetails(String nickname) {
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/players?nickname=" + nickname)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(nickname))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(PlayerDetails.class)
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse)
                .block();
    }

    public PlayerCsgoStatsResponse getPlayerCsgoStats(String playerId) {
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/players/" + playerId + "/stats/csgo")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(PlayerStats.class)
                .map(PlayerStatsMapper::map)
                .block();
    }

    Mono<Position> getPlayerPositionInRegion(String playerId, String region) {
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/rankings/games/csgo/regions/" + region + "/players/" + playerId + "?limit=1")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(Position.class)
                .subscribeOn(Schedulers.fromExecutor(taskExecutor));
    }

    Mono<Position> getPlayerPositionInCountry(String playerId, String region, String country) {
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/rankings/games/csgo/regions/" + region + "/players/" + playerId + "?country=" + country + "&limit=1")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(Position.class)
                .subscribeOn(Schedulers.fromExecutor(taskExecutor));
    }

    private Mono<PlayerNotFoundException> throwClientException(String playerId) {
        throw new PlayerNotFoundException(
                String.format("Player %s could not be found on Faceit.", playerId));
    }

    private Mono<FaceitServerException> throwServerException() {
        throw new FaceitServerException(
                "Faceit server error while requesting %s player csgo stats.");
    }
}
