package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final FaceitMatchClient faceitMatchClient;

    public MatchService(FaceitMatchClient faceitMatchClient) {
        this.faceitMatchClient = faceitMatchClient;
    }

    public SimpleMatchesResponse getMatches(String playerId, Integer from, Integer offset) {
        return faceitMatchClient.getMatches(playerId, from, offset);
    }

    public MatchFullDetailsResponse getMatch(String matchId, String playerId) {
        return faceitMatchClient.getMatchDetails(matchId, playerId);
    }
}
