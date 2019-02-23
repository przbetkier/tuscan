package io.github.przbetkier.tuscan.client.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.config.properties.FaceitMatchesProperties;
import io.github.przbetkier.tuscan.domain.match.MatchFullDetailsMapper;
import io.github.przbetkier.tuscan.domain.match.MatchNotFoundException;
import io.github.przbetkier.tuscan.domain.match.SimpleMatchListMapper;
import io.github.przbetkier.tuscan.exception.FaceitServerException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpMethod.GET;

@Component
public class FaceitMatchClient {

    private final static Integer MATCHES_LIMIT = 20;

    private final WebClient webClient;
    private final FaceitMatchesProperties properties;

    public FaceitMatchClient(@Qualifier("faceitClient") WebClient webClient, FaceitMatchesProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public SimpleMatchesResponse getMatches(String playerId, Integer offset) {

        return webClient
                .method(GET)
                .uri("/players/{playerId}/history?game=csgo&offset="
                        + offset
                        + "&limit="
                        + MATCHES_LIMIT
                        + "&from="
                        + properties.getCutoffDateTimestamp())
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new FaceitServerException(
                            String.format("Faceit server error while requesting %s player matches.", playerId)
                    );
                })
                .bodyToMono(MatchesSimpleDetailsDto.class)
                .map(SimpleMatchListMapper::map)
                .block();
    }

    public MatchFullDetailsResponse getMatchDetails(String matchId, String playerId) {
        return webClient
                .method(GET)
                .uri("/matches/{matchId}/stats", matchId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    throw new MatchNotFoundException(String.format("Match %s could not be found!", matchId));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new FaceitServerException(
                            String.format("Faceit server error while requesting %s match details.", matchId)
                    );
                })
                .bodyToMono(MatchStatsDto.class)
                .map(result -> MatchFullDetailsMapper.map(result, playerId))
                .block();
    }
}
