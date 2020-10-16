package pro.tuscan.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.client.player.PlayerHistoryResponse
import pro.tuscan.domain.player.PlayerService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/faceit/player-history")
class PlayerHistoryEndpoint(private val playerService: PlayerService) {

    @GetMapping("/{playerId}")
    fun getPlayerHistory(@PathVariable playerId: String): Mono<PlayerHistoryResponse> =
            playerService.getPlayerHistory(playerId)
}
