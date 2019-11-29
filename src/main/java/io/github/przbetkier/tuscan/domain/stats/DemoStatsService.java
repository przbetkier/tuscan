package io.github.przbetkier.tuscan.domain.stats;

import io.github.przbetkier.tuscan.adapter.api.request.DemoStatsDto;
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

    public Mono<DemoStats> save(DemoStatsDto request) {
        DemoStats stats = DemoStatsMapper.Companion.mapFromDto(request);
        return demoStatsRepository.save(stats)
                .doOnSuccess(s -> logger.info("Saved new demo stats for match {}.", request.getMatchId()));
    }

    public Mono<DemoStatsDto> getByMatchId(String matchId) {
        return demoStatsRepository.findById(matchId)
                .map(DemoStatsMapper.Companion::mapToDto)
                .switchIfEmpty(Mono.error(new DemoStatsNotFoundException(String.format(
                        "Not found demo stats for match [%s].",
                        matchId))));
    }
}
