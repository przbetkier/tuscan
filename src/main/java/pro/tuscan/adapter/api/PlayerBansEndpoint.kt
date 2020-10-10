package pro.tuscan.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.client.player.BanInfoResponse
import pro.tuscan.domain.player.PlayerService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/faceit/player")
class PlayerBansEndpoint(private val playerService: PlayerService) {

    @GetMapping("{playerId}/bans")
    fun playerBans(@PathVariable playerId: String): Mono<BanInfoResponse> =
            playerService.getPlayerBanInfo(playerId)
}
