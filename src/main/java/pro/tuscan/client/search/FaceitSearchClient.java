package pro.tuscan.client.search;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pro.tuscan.adapter.api.response.PlayerSearchResponse;
import pro.tuscan.client.FaceitClient;
import pro.tuscan.client.RetryInvoker;
import pro.tuscan.domain.player.exception.PlayerNotFoundException;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static java.lang.String.format;

@Component
public class FaceitSearchClient extends FaceitClient {

    private static final int QUERY_RESULT_LIMIT = 20;

    private final WebClient openFaceitClient;
    private final RetryInvoker retryInvoker;

    public FaceitSearchClient(@Qualifier("openFaceitClient") WebClient openFaceitClient, RetryInvoker retryInvoker) {
        this.openFaceitClient = openFaceitClient;
        this.retryInvoker = retryInvoker;
    }

    public Mono<PlayerSearchResponse> getPlayers(String nickname) {
        if (!nickname.isEmpty()) {
            return openFaceitClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder.path("/search/v1")
                            .queryParam("limit", QUERY_RESULT_LIMIT)
                            .queryParam("query", nickname)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> throwClientException(nickname))
                    .onStatus(HttpStatus::is5xxServerError, response -> throwServerException(response.rawStatusCode()))
                    .bodyToMono(FaceitSearchDTO.class)
                    .name("searchPlayer")
                    .metrics()
                    .map(PlayerSearchMapper::map)
                    .retryWhen(retryInvoker.defaultFaceitPolicy("searchPlayer"));
        } else {
            return Mono.just(new PlayerSearchResponse(Collections.emptyList()));
        }
    }

    private Mono<PlayerNotFoundException> throwClientException(String nickname) {
        throw new PlayerNotFoundException(format("Error while searching for players [%s]", nickname));
    }
}
