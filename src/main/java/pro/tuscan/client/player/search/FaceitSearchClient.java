package pro.tuscan.client.player.search;

import pro.tuscan.adapter.api.response.PlayerSearchResponse;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class FaceitSearchClient {

    private static final Logger logger = LoggerFactory.getLogger(FaceitSearchClient.class);
    private static final int QUERY_RESULT_LIMIT = 20;

    private final WebClient openFaceitClient;

    public FaceitSearchClient(@Qualifier("openFaceitClient") WebClient openFaceitClient) {
        this.openFaceitClient = openFaceitClient;
    }

    public Mono<PlayerSearchResponse> getPlayers(String nickname) {
        if (!nickname.isEmpty()) {
            return openFaceitClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder.path("/search/v1")
                            .queryParam("limit", QUERY_RESULT_LIMIT)
                            .queryParam("query", nickname)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        logger.warn("Error while searching for players with nickname: {}", nickname);
                        throw new PlayerNotFoundException("Player not found on Faceit!");
                    })
                    .bodyToMono(FaceitSearchDTO.class)
                    .name("searchPlayer")
                    .metrics()
                    .map(PlayerSearchMapper::map)
                    .doOnError(e -> logger.error("Could not map searched players to response"));
        } else {
            return Mono.just(new PlayerSearchResponse(Collections.emptyList()));
        }
    }
}
