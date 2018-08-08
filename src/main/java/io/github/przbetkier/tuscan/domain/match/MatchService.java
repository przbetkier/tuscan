package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.domain.match.dto.match.MatchesSimpleDetailsDto;
import io.github.przbetkier.tuscan.domain.match.dto.stats.MatchStatsDto;
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

    public SimpleMatchesResponse getMatches(String playerId, Integer offset, Integer from) {
        return webClient
                .method(GET)
                .uri("/players/" + playerId + "/history?game=csgo&offset=" + offset + "&limit=" + MATCHES_LIMIT + "&from=" + from)
                .retrieve()
                .bodyToMono(MatchesSimpleDetailsDto.class)
                .map(SimpleMatchListMapper::map)
                .doOnError(a -> logger.error("Something went wrong", a))
                .block();
    }

    public MatchFullDetailsResponse getMatch(String matchId) {
        return webClient
                .method(GET)
                .uri("/matches/" + matchId + "/stats")
                .retrieve()
                .bodyToMono(MatchStatsDto.class)
                .map(MatchFullDetailsMapper::map)
                .doOnError(a -> logger.error("Could not fetch match with id [{}]", matchId))
                .block();
    }
}
