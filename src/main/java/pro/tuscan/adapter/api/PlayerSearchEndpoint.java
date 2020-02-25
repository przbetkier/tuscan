package pro.tuscan.adapter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.tuscan.adapter.api.response.PlayerSearchResponse;
import pro.tuscan.client.player.search.FaceitSearchClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/faceit/search/players")
class PlayerSearchEndpoint {

    private final FaceitSearchClient faceitSearchClient;

    PlayerSearchEndpoint(FaceitSearchClient faceitSearchClient) {
        this.faceitSearchClient = faceitSearchClient;
    }

    @GetMapping
    public Mono<PlayerSearchResponse> search(@RequestParam String nickname) {
        return faceitSearchClient.getPlayers(nickname);
    }
}
