package pro.tuscan.adapter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.tuscan.adapter.api.response.MatchFullDetailsResponse;
import pro.tuscan.adapter.api.response.SimpleMatchesResponse;
import pro.tuscan.domain.match.MatchService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/faceit/matches")
class MatchesEndpoint {

    private final MatchService matchService;

    MatchesEndpoint(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/simple")
    public Mono<SimpleMatchesResponse> getSimpleMatches(@RequestParam String playerId, @RequestParam Integer offset) {
        return matchService.getMatches(playerId, offset);
    }

    @GetMapping
    public Mono<MatchFullDetailsResponse> getMatchFullDetails(@RequestParam String matchId, @RequestParam String playerId) {
        return matchService.getMatchByPlayer(matchId, playerId);
    }
}
