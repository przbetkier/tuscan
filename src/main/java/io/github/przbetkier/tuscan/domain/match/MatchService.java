package io.github.przbetkier.tuscan.domain.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpMethod.GET;

@Service
public class MatchService {

    private final static Logger logger = LoggerFactory.getLogger(MatchService.class);
    private final static Integer MATCHES_LIMIT = 50;

    private final WebClient webClient;

    public MatchService(WebClient webClient) {
        this.webClient = webClient;
    }

    public MatchesSimpleDetails getMatches(String playerId, Integer offset, Integer from) {
        return webClient
                .method(GET)
                .uri("/players/" + playerId + "/history?game=csgo&offset=" + offset + "&limit=" + MATCHES_LIMIT + "&from=" + from)
                .retrieve()
                .bodyToMono(MatchesSimpleDetails.class)
                .doOnError(a -> logger.error("Something went wrong", a))
                .block();
    }
}
