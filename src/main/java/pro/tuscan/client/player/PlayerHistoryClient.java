package pro.tuscan.client.player;

import pro.tuscan.adapter.api.response.PlayerHistoryResponse;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pro.tuscan.domain.player.PlayerHistoryMapper;
import reactor.core.publisher.Mono;

@Component
public class PlayerHistoryClient {

    private static final Logger logger = LoggerFactory.getLogger(PlayerHistoryClient.class);

    private final WebClient openFaceitClient;

    public PlayerHistoryClient(@Qualifier("openFaceitClient") WebClient openFaceitClient) {
        this.openFaceitClient = openFaceitClient;
    }

    public Mono<PlayerHistoryResponse> getPlayerHistory(String playerId) {
        return openFaceitClient.method(HttpMethod.GET)
                .uri("/stats/api/v1/stats/time/users/{playerId}/games/csgo", playerId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.warn("Player [{}] could not be found on Faceit.", playerId);
                    throw new PlayerNotFoundException("Player not found on Faceit!");
                })
                .bodyToMono(PlayerHistoryDto.class)
                .name("playerHistory")
                .metrics()
                .map(history -> PlayerHistoryMapper.map(history.getMatchHistoryDtoList()))
                .doOnError(e -> logger.error("Could not map player history to response"));
    }
}
