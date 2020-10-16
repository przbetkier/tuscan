package pro.tuscan.client.player;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pro.tuscan.adapter.api.PlayerDetailsResponse;
import pro.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import pro.tuscan.client.FaceitClient;
import pro.tuscan.client.RetryInvoker;
import pro.tuscan.domain.player.PlayerDetailsMapper;
import pro.tuscan.domain.player.PlayerStatsMapper;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpMethod.GET;

@Component
public class FaceitPlayerClient extends FaceitClient {

    private final WebClient faceitClient;
    private final RetryInvoker retryInvoker;

    public FaceitPlayerClient(@Qualifier("faceitClient") WebClient faceitClient, RetryInvoker retryInvoker) {
        this.faceitClient = faceitClient;
        this.retryInvoker = retryInvoker;
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
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse)
                .retryWhen(retryInvoker.defaultFaceitPolicy("playerDetails"));
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
                .map(PlayerStatsMapper::map)
                .retryWhen(retryInvoker.defaultFaceitPolicy("csgoStats"));
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
                .retryWhen(retryInvoker.defaultFaceitPolicy("positionInRegion"));
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
                .retryWhen(retryInvoker.defaultFaceitPolicy("positionInCountry"));
    }

    private static Mono<PlayerNotFoundException> throwClientException(String playerId) {
        throw new PlayerNotFoundException(String.format("Player %s could not be found on Faceit.", playerId));
    }
}
