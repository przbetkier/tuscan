package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerHistoryResponse;
import io.github.przbetkier.tuscan.domain.player.PlayerService;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faceit/player-history")
@Timed("endpoint.playerHistory")
class PlayerHistoryEndpoint {

    private final PlayerService playerService;

    PlayerHistoryEndpoint(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}")
    PlayerHistoryResponse getPlayerHistory(@PathVariable String playerId) {
        return playerService.getPlayerHistory(playerId);
    }
}
