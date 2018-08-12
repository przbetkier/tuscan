package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.domain.match.dto.player.PlayerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
class FaceitPlayerClient {

    private final static Logger logger = LoggerFactory.getLogger(FaceitPlayerClient.class);

    private final WebClient webClient;

    public FaceitPlayerClient(WebClient webClient) {
        this.webClient = webClient;
    }

    PlayerDetailsResponse getPlayerDetails(String nickname) {
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
