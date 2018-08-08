package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.domain.player.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faceit/players/details")
public class PlayerDetailsEndpoint {

    private final static Logger logger = LoggerFactory.getLogger(PlayerDetailsEndpoint.class);

    private final PlayerService playerService;

    public PlayerDetailsEndpoint(PlayerService playerService) {
        this.playerService = playerService;
    }

    @CrossOrigin
    @GetMapping()
    public PlayerDetailsResponse getPlayerDetails(@RequestParam String nickname) {
        return playerService.getPlayerDetails(nickname);
    }
}
