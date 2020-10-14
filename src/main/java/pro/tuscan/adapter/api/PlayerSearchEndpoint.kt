package pro.tuscan.adapter.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.adapter.api.response.PlayerSearchResponse
import pro.tuscan.client.search.FaceitSearchClient
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/faceit/search/players")
class PlayerSearchEndpoint(private val faceitSearchClient: FaceitSearchClient) {

    @GetMapping
    fun search(@RequestParam nickname: String): Mono<PlayerSearchResponse> = faceitSearchClient.getPlayers(nickname)
}
