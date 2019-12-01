package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.request.DemoStatsDto;
import io.github.przbetkier.tuscan.adapter.api.request.LambdaInvokerRequest;
import io.github.przbetkier.tuscan.domain.stats.DemoStats;
import io.github.przbetkier.tuscan.domain.stats.DemoStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tuscan-api/demo-stats")
class DemoParserEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(DemoParserEndpoint.class);

    private final DemoStatsService demoStatsService;

    public DemoParserEndpoint(DemoStatsService demoStatsService) {
        this.demoStatsService = demoStatsService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<DemoStats> create(@RequestBody DemoStatsDto demoDetailsRequest) {
        return demoStatsService.save(demoDetailsRequest);
    }

    @GetMapping("/{matchId}")
    public Mono<DemoStatsDto> get(@PathVariable String matchId) {
        return demoStatsService.getByMatchId(matchId);
    }

    @PostMapping("/invoke")
    @ResponseStatus(CREATED)
    public Mono invoke(@RequestBody LambdaInvokerRequest request) {
        logger.info("Requested lambda invocation for match {}", request.getMatchId());
        return demoStatsService.invokeLambda(request.getMatchId());
    }
}
