package io.github.przbetkier.tuscan.client.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.config.properties.FaceitWebClientProperties;
import io.github.przbetkier.tuscan.domain.player.PlayerDetailsMapper;
import io.github.przbetkier.tuscan.domain.player.PlayerStatsMapper;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import io.github.przbetkier.tuscan.exception.FaceitServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpMethod.GET;
import static reactor.core.scheduler.Schedulers.parallel;
import static reactor.retry.Retry.anyOf;

@Component
public class FaceitPlayerClient {

    private final static Logger logger = LoggerFactory.getLogger(FaceitPlayerClient.class);

    private final WebClient faceitClient;
    private final FaceitWebClientProperties properties;

    public FaceitPlayerClient(@Qualifier("faceitClient") WebClient faceitClient, FaceitWebClientProperties properties) {
        this.faceitClient = faceitClient;
        this.properties = properties;
    }

    public PlayerDetailsResponse getPlayerDetails(String nickname) {
        return faceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/players").queryParam("nickname", nickname).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(nickname))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(PlayerDetails.class)
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse)
                .block();
    }

    public PlayerCsgoStatsResponse getPlayerCsgoStats(String playerId) {
        return faceitClient.method(GET)
                .uri("/players/{playerId}/stats/csgo", playerId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(PlayerStats.class)
                .map(PlayerStatsMapper::map)
                .block();
    }

    public Mono<Position> getPlayerPositionInRegion(String playerId, String region) {
        return faceitClient.method(GET)
                .uri("/rankings/games/csgo/regions/{region}/players/{playerId}?limit=1", region, playerId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(Position.class)
                .retryWhen(anyOf(FaceitServerException.class).retryMax(properties.getRetry().getMaxRetries())
                                   .randomBackoff(properties.getRetry().getMin(), properties.getRetry().getMax()))
                .subscribeOn(parallel());
    }

    public Mono<Position> getPlayerPositionInCountry(String playerId, String region, String country) {
        return faceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/rankings/games/csgo/regions/{region}/players/{playerId}")
                        .queryParam("country", country)
                        .queryParam("limit", 1)
                        .build(region, playerId))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException())
                .bodyToMono(Position.class)
                .retryWhen(anyOf(FaceitServerException.class)
                                   .retryMax(properties.getRetry().getMaxRetries())
                                   .randomBackoff(properties.getRetry().getMin(), properties.getRetry().getMax()))
                .subscribeOn(parallel());
    }

    private Mono<PlayerNotFoundException> throwClientException(String playerId) {
        throw new PlayerNotFoundException(String.format("Player %s could not be found on Faceit.", playerId));
    }

    private Mono<FaceitServerException> throwServerException() {
        logger.warn("Faceit request failed [5xx error].");
        throw new FaceitServerException("Faceit server error while requesting player csgo stats.");
    }
}
