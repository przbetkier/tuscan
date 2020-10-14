package pro.tuscan.adapter.api

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import pro.tuscan.domain.player.PlayerService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/faceit/players/details")
class PlayerDetailsEndpoint(private val playerService: PlayerService) {

    private val logger = LoggerFactory.getLogger(PlayerDetailsEndpoint::class.java)

    @GetMapping
    fun getPlayerDetails(@RequestParam nickname: String): Mono<PlayerDetailsResponse> {
        logger.info("Player details requested for [$nickname].")
        return playerService.getPlayerDetails(nickname)
    }

    @GetMapping("/csgo/{playerId}")
    fun getCsgoStats(@PathVariable playerId: String): Mono<PlayerCsgoStatsResponse> =
            playerService.getCsgoStats(playerId)
}

data class PlayerDetailsResponse(val playerId: String,
                                 val nickname: String,
                                 val gameDetails: GameDetails?,
                                 val avatarUrl: String,
                                 val country: String,
                                 val membership: Membership)

data class GameDetails(val faceitElo: Int?,
                       val level: Int?,
                       val region: String,
                       val steamId: String)

enum class Membership(val membership: String) {
    PREMIUM("premium"),
    UNLIMITED("unlimited"),
    FREE("free"),
    CSGO("csgo")
}
