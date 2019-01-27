package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerSearchResponse;
import io.github.przbetkier.tuscan.client.player.search.FaceitSearchClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faceit/search/players")
class PlayerSearchEndpoint {

    private final FaceitSearchClient faceitSearchClient;

    PlayerSearchEndpoint(FaceitSearchClient faceitSearchClient) {
        this.faceitSearchClient = faceitSearchClient;
    }

    @GetMapping
    PlayerSearchResponse search(@RequestParam String nickname) {
        return faceitSearchClient.getPlayers(nickname);
    }
}
