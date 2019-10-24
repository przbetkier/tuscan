package io.github.przbetkier.tuscan.client.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.config.properties.FaceitMatchesProperties;
import io.github.przbetkier.tuscan.domain.match.MatchFullDetailsMapper;
import io.github.przbetkier.tuscan.domain.match.MatchNotFoundException;
import io.github.przbetkier.tuscan.domain.match.SimpleMatchListMapper;
import io.github.przbetkier.tuscan.exception.FaceitServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class FaceitMatchClient {

    private static final Integer MATCHES_LIMIT = 20;
    private static final Logger logger = LoggerFactory.getLogger(FaceitMatchClient.class);

    private final WebClient faceitClient;
    private final WebClient openFaceitClient;
    private final FaceitMatchesProperties properties;

    public FaceitMatchClient(@Qualifier("faceitClient") WebClient faceitClient,
                             @Qualifier("openFaceitClient") WebClient openFaceitClient,
                             FaceitMatchesProperties properties) {
        this.faceitClient = faceitClient;
        this.openFaceitClient = openFaceitClient;
        this.properties = properties;
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
                .onStatus(HttpStatus::is5xxServerError,
                          clientResponse -> Mono.error(new FaceitServerException(String.format(
                                  "Faceit server error while requesting %s player matches.",
                                  playerId))))
                .bodyToMono(MatchesSimpleDetailsDto.class)
                .name("matches")
                .metrics()
                .map(SimpleMatchListMapper::map);
    }

    public Mono<SimpleMatchesResponse> fallbackToV1Matches(String playerId) {
        logger.warn("Fallback to v1 API for simple matches.");
        return openFaceitClient.method(GET)
                .uri(uriBuilder -> uriBuilder.path("/stats/api/v1/stats/time/users/{playerId}/games/csgo")
                        .queryParam("page", 0)
                        .queryParam("size", MATCHES_LIMIT)
                        .build(playerId))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new FaceitServerException(String.format(
                            "Faceit server error while requesting %s player matches [v1 API fallback].",
                            playerId));
                })
                .bodyToMono(new ParameterizedTypeReference<List<OpenMatchSimpleDetailsDto>>() {})
                .name("matchesV1Fallback")
                .metrics()
                .map(SimpleMatchListMapper::mapForOpenApi);
    }

    public MatchFullDetailsResponse getMatchDetails(String matchId, String playerId) {
        return getMatchStats(matchId, playerId)
                .zipWith(getDemoUrl(matchId))
                .map(result -> MatchFullDetailsMapper.Companion.map(result.getT1(), playerId, result.getT2()))
                .block();
    }


    private Mono<MatchStatsDto> getMatchStats(String matchId, String playerId) {
        return faceitClient.method(GET)
                .uri("/matches/{matchId}/stats", matchId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    throw new MatchNotFoundException(String.format("Match %s could not be found!", matchId));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new FaceitServerException(String.format(
                            "Faceit server error while requesting %s match details.",
                            matchId));
                })
                .bodyToMono(MatchStatsDto.class)
                .name("matchDetails")
                .metrics();
    }

    private Mono<MatchDemoDto> getDemoUrl(String matchId) {
        return faceitClient
                .get()
                .uri("/matches/{matchId}", matchId)
                .retrieve()
                .bodyToMono(MatchDemoDto.class)
                .onErrorResume(result -> Mono.just(new MatchDemoDto(Collections.emptyList())))
                .name("matchDemo")
                .metrics();
    }
}
