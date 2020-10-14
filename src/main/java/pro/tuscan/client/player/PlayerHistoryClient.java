package pro.tuscan.client.player;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pro.tuscan.adapter.api.response.PlayerHistoryResponse;
import pro.tuscan.client.FaceitClient;
import pro.tuscan.client.RetryInvoker;
import pro.tuscan.domain.player.PlayerHistoryMapper;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class PlayerHistoryClient extends FaceitClient {

    private final WebClient openFaceitClient;
    private final RetryInvoker retryInvoker;

    public PlayerHistoryClient(@Qualifier("openFaceitClient") WebClient openFaceitClient, RetryInvoker retryInvoker) {
        this.openFaceitClient = openFaceitClient;
        this.retryInvoker = retryInvoker;
    }

    public Mono<PlayerHistoryResponse> getPlayerHistory(String playerId) {
        return openFaceitClient.method(GET)
                .uri("/stats/api/v1/stats/time/users/{playerId}/games/csgo", playerId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> throwClientException(playerId))
                .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                .bodyToMono(new ParameterizedTypeReference<List<MatchHistoryDto>>() {})
                .name("playerHistory")
                .metrics()
                .map(PlayerHistoryMapper::map)
                .retryWhen(retryInvoker.defaultFaceitPolicy("playerHistory"));
    }

    private static Mono<PlayerNotFoundException> throwClientException(String playerId) {
        throw new PlayerNotFoundException(String.format("Player [%s] history could not be found on Faceit.", playerId));
    }
}
