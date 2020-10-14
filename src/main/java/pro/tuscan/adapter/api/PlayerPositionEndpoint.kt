package pro.tuscan.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.adapter.api.response.PlayerPositionResponse
import pro.tuscan.domain.player.PlayerService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/faceit/player/position")
class PlayerPositionEndpoint(private val playerService: PlayerService) {

    @GetMapping
    fun getPlayerPosition(@RequestParam playerId: String,
                          @RequestParam region: String,
                          @RequestParam country: String): Mono<PlayerPositionResponse> =
            playerService.getPlayerPosition(playerId, region, country)
}
