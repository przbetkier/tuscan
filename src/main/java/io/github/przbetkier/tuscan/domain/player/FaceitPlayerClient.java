package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.domain.match.dto.player.PlayerDetails;
import io.github.przbetkier.tuscan.domain.match.dto.player.stats.PlayerStats;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.warn("Player [{}] could not be found on Faceit.", nickname);
                    throw new PlayerNotFoundException("Player not found on Faceit!");
                })
                .bodyToMono(PlayerDetails.class)
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse)
                .doOnError(a -> logger.error("Something went wrong", a))
                .block();
    }

    PlayerCsgoStatsResponse getPlayerCsgoStats(String playerId) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/players/" + playerId + "/stats/csgo")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.warn("Player [{}] could not be found on Faceit.", playerId);
                    throw new PlayerNotFoundException("Player not found on Faceit!");
                })
                .bodyToMono(PlayerStats.class)
                .map(PlayerStatsMapper::map)
                .doOnError(e -> logger.error("Could not map segments to player stats.", e))
                .block();
    }
}
