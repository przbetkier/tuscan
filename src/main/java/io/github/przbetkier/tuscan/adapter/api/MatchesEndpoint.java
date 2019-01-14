package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.SimpleMatchesResponse;
import io.github.przbetkier.tuscan.domain.match.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faceit/matches")
class MatchesEndpoint {

    private final MatchService matchService;

    MatchesEndpoint(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/simple")
    SimpleMatchesResponse getSimpleMatches(@RequestParam String playerId,
                                           @RequestParam Integer offset) {
        return matchService.getMatches(playerId, offset);
    }

    @GetMapping
    MatchFullDetailsResponse getMatchFullDetails(@RequestParam String matchId, @RequestParam String playerId) {
        return matchService.getMatch(matchId, playerId);
    }
}
