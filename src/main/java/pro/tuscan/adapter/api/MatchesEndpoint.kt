package pro.tuscan.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.adapter.api.response.MatchFullDetailsResponse
import pro.tuscan.adapter.api.response.SimpleMatchesResponse
import pro.tuscan.domain.match.MatchService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/faceit/matches")
class MatchesEndpoint(private val matchService: MatchService) {

    @GetMapping
    fun getMatchFullDetails(@RequestParam matchId: String,
                            @RequestParam playerId: String): Mono<MatchFullDetailsResponse> =
            matchService.getMatchByPlayer(matchId, playerId)

    @GetMapping("/simple")
    fun getSimpleMatches(@RequestParam playerId: String,
                         @RequestParam offset: Int): Mono<SimpleMatchesResponse> =
            matchService.getMatches(playerId, offset)
}
