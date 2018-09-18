package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.domain.player.dto.stats.PlayerStats;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
class FaceitPlayerClient {

    private final static Logger logger = LoggerFactory.getLogger(FaceitPlayerClient.class);

    private final WebClient faceitClient;

    public FaceitPlayerClient(@Qualifier("faceitClient") WebClient faceitClient) {
        this.faceitClient = faceitClient;
    }

    PlayerDetailsResponse getPlayerDetails(String nickname) {
        return faceitClient
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
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/players/" + playerId + "/stats/csgo")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.warn("Player [{}] could not be found on Faceit.", playerId);
                    throw new PlayerNotFoundException("Player not found on Faceit!");
                })
                .bodyToMono(PlayerStats.class)
                .map(PlayerStatsMapper::map)
                .doOnError(e -> logger.error("Could not map segments to dto stats.", e))
                .block();
    }
}
