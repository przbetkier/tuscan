package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.request.DemoDetailsRequest;
import io.github.przbetkier.tuscan.domain.stats.DemoStats;
import io.github.przbetkier.tuscan.domain.stats.DemoStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tuscan-api/demo-parser")
class DemoParserEndpoint {

    private final DemoStatsService demoStatsService;

    public DemoParserEndpoint(DemoStatsService demoStatsService) {
        this.demoStatsService = demoStatsService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<DemoStats> create(@RequestBody DemoDetailsRequest demoDetailsRequest) {
        return demoStatsService.save(demoDetailsRequest);
    }
}
