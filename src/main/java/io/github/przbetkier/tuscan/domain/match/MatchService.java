package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final FaceitMatchClient faceitMatchClient;

    public MatchService(FaceitMatchClient faceitMatchClient) {
        this.faceitMatchClient = faceitMatchClient;
    }

    @Cacheable(value = "simple_matches", key = "#playerId")
    public SimpleMatchesResponse getMatches(String playerId, Integer from, Integer offset) {
        return faceitMatchClient.getMatches(playerId, from, offset);
    }

    @Cacheable(value = "detailed_matches", key = "#matchId")
    public MatchFullDetailsResponse getMatch(String matchId) {
        return faceitMatchClient.getMatchDetails(matchId);
    }
}
