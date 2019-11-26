package io.github.przbetkier.tuscan.domain.stats;

import io.github.przbetkier.tuscan.adapter.api.request.DemoDetailsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DemoStatsService {

    private static final Logger logger = LoggerFactory.getLogger(DemoStatsService.class);

    private final DemoStatsRepository demoStatsRepository;

    public DemoStatsService(DemoStatsRepository demoStatsRepository) {
        this.demoStatsRepository = demoStatsRepository;
    }

    public Mono<DemoStats> save(DemoDetailsRequest request) {
        DemoStats stats = DemoStatsMapper.Companion.map(request);
        return demoStatsRepository.save(stats)
                .doOnSuccess(s -> logger.info("Saved new demo stats for match {}.", request.getMatchId()));
    }
}
