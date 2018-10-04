package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerPositionResponse;
import io.github.przbetkier.tuscan.domain.player.PlayerService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@CrossOrigin // FIXME: To config
@RestController
@RequestMapping("/faceit/player")
public class PlayerPositionEndpoint {

    private final PlayerService playerService;
    private final TaskExecutor taskExecutor;

    public PlayerPositionEndpoint(PlayerService playerService, TaskExecutor taskExecutor) {
        this.playerService = playerService;
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/position")
    public Mono<PlayerPositionResponse> getPlayerPosition(@RequestParam String playerId,
                                                          @RequestParam String region,
                                                          @RequestParam String country) {
        return playerService.getPlayerPosition(playerId, region, country)
                .subscribeOn(Schedulers.fromExecutor(taskExecutor));
    }
}
