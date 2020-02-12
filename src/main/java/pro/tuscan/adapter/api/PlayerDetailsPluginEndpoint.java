package pro.tuscan.adapter.api;

import pro.tuscan.adapter.api.response.DetailedPlayerCsgoStatsResponse;
import pro.tuscan.domain.player.PlayerService;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value = "/tuscan-api/plugin/players/details")
@Timed("endpoint.playerDetailsPlugin")
class PlayerDetailsPluginEndpoint {

    private final PlayerService playerService;

    PlayerDetailsPluginEndpoint(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/csgo")
    public Flux<DetailedPlayerCsgoStatsResponse> getMultiPlayerDetails(
            @RequestParam(value = "nickname") List<String> nicknames) {
        return playerService.getMultiPlayerDetails(nicknames);
    }

}
