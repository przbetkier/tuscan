package io.github.przbetkier.tuscan.domain.player;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.domain.player.dto.stats.PlayerStats;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import io.github.przbetkier.tuscan.exception.FaceitServerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FaceitPlayerClient {

    private final WebClient faceitClient;

    public FaceitPlayerClient(@Qualifier("faceitClient") WebClient faceitClient) {
        this.faceitClient = faceitClient;
    }

    public PlayerDetailsResponse getPlayerDetails(String nickname) {
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/players?nickname=" + nickname)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    throw new PlayerNotFoundException(
                            String.format("Player %s could not be found on Faceit.", nickname));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new FaceitServerException(
                            String.format("Faceit server error while requesting %s player details.", nickname));
                })
                .bodyToMono(PlayerDetails.class)
                .map(PlayerDetailsMapper::mapToPlayerDetailsResponse)
                .block();
    }

    public PlayerCsgoStatsResponse getPlayerCsgoStats(String playerId) {
        return faceitClient
                .method(HttpMethod.GET)
                .uri("/players/" + playerId + "/stats/csgo")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    throw new PlayerNotFoundException(
                            String.format("Player %s could not be found on Faceit.", playerId));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new FaceitServerException(
                            String.format("Faceit server error while requesting %s player csgo stats.", playerId));
                })
                .bodyToMono(PlayerStats.class)
                .map(PlayerStatsMapper::map)
                .block();
    }
}
