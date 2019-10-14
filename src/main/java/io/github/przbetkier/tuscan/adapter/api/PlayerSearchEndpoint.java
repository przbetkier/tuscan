package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerSearchResponse;
import io.github.przbetkier.tuscan.client.player.search.FaceitSearchClient;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Timed("endpoint.playerSearch")
@RequestMapping("/faceit/search/players")
class PlayerSearchEndpoint {

    private final FaceitSearchClient faceitSearchClient;

    PlayerSearchEndpoint(FaceitSearchClient faceitSearchClient) {
        this.faceitSearchClient = faceitSearchClient;
    }

    @GetMapping
    Mono<PlayerSearchResponse> search(@RequestParam String nickname) {
        return faceitSearchClient.getPlayers(nickname);
    }
}
