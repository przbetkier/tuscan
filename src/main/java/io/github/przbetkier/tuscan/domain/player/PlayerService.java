package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final WebClient webClient;

    public PlayerService(WebClient webClient) {
        this.webClient = webClient;
    }

    public PlayerDetailsResponse getPlayerDetails(String nickname) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/players?nickname=" + nickname)
                .retrieve()
                .bodyToMono(PlayerDetails.class)
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse)
                .doOnError(a -> logger.error("Something went wrong", a))
                .block();
    }
}
