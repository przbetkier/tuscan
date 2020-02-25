package pro.tuscan.client.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pro.tuscan.adapter.api.response.MatchFullDetailsResponse;
import pro.tuscan.adapter.api.response.SimpleMatchesResponse;
import pro.tuscan.client.FaceitClient;
import pro.tuscan.client.RetryInvoker;
import pro.tuscan.config.properties.FaceitMatchesProperties;
import pro.tuscan.domain.match.MatchFullDetailsMapper;
import pro.tuscan.domain.match.SimpleMatchListMapper;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class FaceitMatchClient extends FaceitClient {

    private static final Integer MATCHES_LIMIT = 20;
    private static final Logger logger = LoggerFactory.getLogger(FaceitMatchClient.class);

    private final WebClient faceitClient;
    private final WebClient openFaceitClient;
    private final FaceitMatchesProperties properties;
    private final RetryInvoker retryInvoker;

    public FaceitMatchClient(@Qualifier("faceitClient") WebClient faceitClient,
                             @Qualifier("openFaceitClient") WebClient openFaceitClient,
                             FaceitMatchesProperties properties, RetryInvoker retryInvoker) {
        this.faceitClient = faceitClient;
        this.openFaceitClient = openFaceitClient;
        this.properties = properties;
        this.retryInvoker = retryInvoker;
    }

    public Mono<SimpleMatchesResponse> getMatches(String playerId, Integer offset) {
        return faceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/players/{playerId}/history")
                        .queryParam("game", "csgo")
                        .queryParam("offset", offset)
                        .queryParam("limit", MATCHES_LIMIT)
                        .queryParam("from", properties.getCutoffDateTimestamp())
                        .build(playerId))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(MatchesSimpleDetailsDto.class)
                .name("matches")
                .metrics()
                .map(SimpleMatchListMapper::map)
                .retryWhen(retryInvoker.defaultFaceitPolicy("matches"));
    }

    public Mono<SimpleMatchesResponse> fallbackToV1Matches(String playerId) {
        logger.warn("Fallback to v1 API for simple matches.");
        return openFaceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/stats/api/v1/stats/time/users/{playerId}/games/csgo")
                        .queryParam("page", 0)
                        .queryParam("size", MATCHES_LIMIT)
                        .build(playerId))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(new ParameterizedTypeReference<List<OpenMatchSimpleDetailsDto>>() {})
                .name("matchesV1Fallback")
                .metrics()
                .map(SimpleMatchListMapper::mapForOpenApi)
                .doOnError(e -> logger.warn("Error while requesting {} player matches [v1 API fallback]", playerId));
        // No retries here
    }

    public Mono<MatchFullDetailsResponse> getMatchDetails(String matchId, String playerId) {
        return getMatchStats(matchId).zipWith(getDemoUrl(matchId))
                .map(result -> MatchFullDetailsMapper.Companion.map(result.getT1(), playerId, result.getT2()));
    }

    private Mono<MatchStatsDto> getMatchStats(String matchId) {
        return faceitClient.method(GET)
                .uri("/matches/{matchId}/stats", matchId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(matchId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(MatchStatsDto.class)
                .name("matchDetails")
                .metrics()
                .retryWhen(retryInvoker.defaultFaceitPolicy("matchDetails"));
    }

    private Mono<MatchDemoDto> getDemoUrl(String matchId) {
        return faceitClient.get()
                .uri("/matches/{matchId}", matchId)
                .retrieve()
                .bodyToMono(MatchDemoDto.class)
                .onErrorResume(result -> Mono.just(new MatchDemoDto(Collections.emptyList())))
                .name("matchDemo")
                .metrics()
                .retryWhen(retryInvoker.defaultFaceitPolicy("matchDemo"));
    }

    private static Mono<PlayerNotFoundException> throwClientException(String matchId) {
        throw new PlayerNotFoundException(String.format("Match %s could not be found!", matchId));
    }
}
