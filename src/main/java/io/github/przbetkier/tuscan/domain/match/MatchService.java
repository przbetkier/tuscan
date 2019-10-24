package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.client.match.FaceitMatchClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MatchService {

    private final FaceitMatchClient faceitMatchClient;

    public MatchService(FaceitMatchClient faceitMatchClient) {
        this.faceitMatchClient = faceitMatchClient;
    }

    public Mono<SimpleMatchesResponse> getMatches(String playerId, Integer offset) {
        return faceitMatchClient.getMatches(playerId, offset)
                .onErrorResume(e -> faceitMatchClient.fallbackToV1Matches(playerId));
    }

    @Cacheable(value = "playerMatch", key = "{#playerId, #matchId}")
    public Mono<MatchFullDetailsResponse> getMatch(String matchId, String playerId) {
        return faceitMatchClient.getMatchDetails(matchId, playerId);
    }
}
