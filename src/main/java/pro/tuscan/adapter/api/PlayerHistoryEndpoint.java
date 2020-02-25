package pro.tuscan.adapter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.tuscan.adapter.api.response.PlayerHistoryResponse;
import pro.tuscan.domain.player.PlayerService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/faceit/player-history")
class PlayerHistoryEndpoint {

    private final PlayerService playerService;

    PlayerHistoryEndpoint(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}")
    public Mono<PlayerHistoryResponse> getPlayerHistory(@PathVariable String playerId) {
        return playerService.getPlayerHistory(playerId);
    }
}
