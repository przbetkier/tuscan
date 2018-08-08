package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.domain.match.MatchService;
import io.github.przbetkier.tuscan.domain.match.MatchesSimpleDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faceit/matches")
public class MatchesEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MatchesEndpoint.class);

    private final MatchService matchService;

    public MatchesEndpoint(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    MatchesSimpleDetails getMatchesSimpleDetails(@RequestParam String playerId,
                                                 @RequestParam(required = false) Integer from,
                                                 @RequestParam Integer offset) {
        return matchService.getMatches(playerId, from, offset);
    }
}
