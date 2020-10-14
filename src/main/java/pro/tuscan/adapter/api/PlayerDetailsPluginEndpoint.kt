package pro.tuscan.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.adapter.api.response.DetailedPlayerCsgoStatsResponse
import pro.tuscan.domain.player.PlayerService
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/tuscan-api/plugin/players/details")
class PlayerDetailsPluginEndpoint(private val playerService: PlayerService) {

    @GetMapping("/csgo")
    fun getMultiPlayerDetails(
            @RequestParam(value = "nickname") nicknames: List<String>
    ): Flux<DetailedPlayerCsgoStatsResponse> =
            playerService.getMultiPlayerDetails(nicknames)
}
