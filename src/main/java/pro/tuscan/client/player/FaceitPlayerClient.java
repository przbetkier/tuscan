package pro.tuscan.client.player;

import pro.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import pro.tuscan.adapter.api.response.PlayerDetailsResponse;
import pro.tuscan.config.properties.FaceitWebClientProperties;
import pro.tuscan.domain.player.PlayerDetailsMapper;
import pro.tuscan.domain.player.PlayerStatsMapper;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;
import pro.tuscan.exception.FaceitServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import static org.springframework.http.HttpMethod.GET;
import static reactor.retry.Retry.anyOf;

@Component
public class FaceitPlayerClient {

    private static final Logger logger = LoggerFactory.getLogger(FaceitPlayerClient.class);

    private final WebClient faceitClient;
    private final FaceitWebClientProperties properties;

    public FaceitPlayerClient(@Qualifier("faceitClient") WebClient faceitClient, FaceitWebClientProperties properties) {
        this.faceitClient = faceitClient;
        this.properties = properties;
    }

    public Mono<PlayerDetailsResponse> getPlayerDetails(String nickname) {
        return faceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/players").queryParam("nickname", nickname).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(nickname))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(PlayerDetails.class)
                .name("playerDetails")
                .metrics()
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse);
    }

    public Mono<PlayerCsgoStatsResponse> getPlayerCsgoStats(String playerId) {
        return faceitClient.method(GET)
                .uri("/players/{playerId}/stats/csgo", playerId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(PlayerStats.class)
                .name("csgoStats")
                .metrics()
                .map(PlayerStatsMapper.Companion::map);
    }

    public Mono<Position> getPlayerPositionInRegion(String playerId, String region) {
        return faceitClient.method(GET)
                .uri("/rankings/games/csgo/regions/{region}/players/{playerId}?limit=1", region, playerId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(Position.class)
                .name("positionInRegion")
                .metrics()
                .retryWhen(Retry.anyOf(FaceitServerException.class)
                                   .retryMax(properties.getRetry().getMaxRetries())
                                   .randomBackoff(properties.getRetry().getMin(), properties.getRetry().getMax()));
    }

    public Mono<Position> getPlayerPositionInCountry(String playerId, String region, String country) {
        return faceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/rankings/games/csgo/regions/{region}/players/{playerId}")
                        .queryParam("country", country)
                        .queryParam("limit", 1)
                        .build(region, playerId))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(Position.class)
                .name("positionInCountry")
                .metrics()
                .retryWhen(anyOf(FaceitServerException.class).retryMax(properties.getRetry().getMaxRetries())
                                   .randomBackoff(properties.getRetry().getMin(), properties.getRetry().getMax()));
    }

    private Mono<PlayerNotFoundException> throwClientException(String playerId) {
        throw new PlayerNotFoundException(String.format("Player %s could not be found on Faceit.", playerId));
    }

    private Mono<FaceitServerException> throwServerException(int statusCode) {
        logger.warn("Faceit request failed [{}} error].", statusCode);
        throw new FaceitServerException("Faceit server error occurred.");
    }
}
