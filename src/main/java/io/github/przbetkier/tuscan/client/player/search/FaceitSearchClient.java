package io.github.przbetkier.tuscan.client.player.search;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerSearchResponse;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Component
public class FaceitSearchClient {

    private final static Logger logger = LoggerFactory.getLogger(FaceitSearchClient.class);
    private final static int QUERY_RESULT_LIMIT = 20;

    private final WebClient openFaceitClient;

    public FaceitSearchClient(@Qualifier("openFaceitClient") WebClient openFaceitClient) {
        this.openFaceitClient = openFaceitClient;
    }

    public PlayerSearchResponse getPlayers(String nickname) {
        if (!nickname.isEmpty()) {
            return openFaceitClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder.path("/search/v1")
                            .queryParam("limit", QUERY_RESULT_LIMIT)
                            .queryParam("query", nickname)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        logger.warn("Error while searching for players with nickname: ", nickname);
                        throw new PlayerNotFoundException("Player not found on Faceit!");
                    })
                    .bodyToMono(FaceitSearchDTO.class)
                    .map(PlayerSearchMapper::map)
                    .doOnError(e -> logger.error("Could not map searched players to response"))
                    .block();
        } else {
            return new PlayerSearchResponse(Collections.emptyList());
        }
    }
}
