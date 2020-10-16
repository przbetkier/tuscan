package pro.tuscan.domain.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.tuscan.adapter.api.LambdaRequest;
import pro.tuscan.adapter.api.LambdaResponse;
import pro.tuscan.adapter.api.request.DemoStatsDto;
import pro.tuscan.client.lambda.LambdaDemoParserClient;
import pro.tuscan.domain.match.DemoStatus;
import pro.tuscan.domain.match.Match;
import pro.tuscan.domain.match.MatchService;
import reactor.core.publisher.Mono;

import static pro.tuscan.domain.match.DemoStatus.COMPUTING;
import static pro.tuscan.domain.match.DemoStatus.NO_ACTION;
import static pro.tuscan.domain.match.DemoStatus.PARSED;

@Service
public class DemoStatsService {

    private static final Logger logger = LoggerFactory.getLogger(DemoStatsService.class);

    private final DemoStatsRepository demoStatsRepository;
    private final MatchService matchService;
    private final LambdaDemoParserClient lambdaClient;

    public DemoStatsService(DemoStatsRepository demoStatsRepository, MatchService matchService,
                            LambdaDemoParserClient lambdaClient) {
        this.demoStatsRepository = demoStatsRepository;
        this.matchService = matchService;
        this.lambdaClient = lambdaClient;
    }

    public Mono<DemoStats> save(DemoStatsDto request) {
        DemoStats stats = DemoStatsMapper.Companion.mapFromDto(request);
        return demoStatsRepository.save(stats).doOnSuccess(s -> {
            logger.info("Saved new demo stats for match {}.", request.getMatchId());
            updateMatchWithStatus(s.getMatchId(), PARSED).subscribe();
        });
    }

    public Mono<DemoStatsDto> getByMatchId(String matchId) {
        return demoStatsRepository.findById(matchId)
                .map(DemoStatsMapper.Companion::mapToDto)
                .switchIfEmpty(Mono.error(new DemoStatsNotFoundException(String.format(
                        "Not found demo stats for match [%s].",
                        matchId))));
    }

    public Mono<LambdaResponse> invokeLambda(String matchId) {
        return matchService.getMatch(matchId)
                .filter(match -> match.getDemoStatus() != COMPUTING
                        && match.getDemoStatus() != PARSED)
                .switchIfEmpty(Mono.error(new DemoStatsException("Match is parsed or parsing has been already requested")))
                .then(updateMatchWithStatus(matchId, COMPUTING))
                .map(match -> new LambdaRequest(match.getMatchId(), match.getDemoUrl()))
                .flatMap(lambdaClient::invokeLambda)
                .doOnSuccess(resp -> logger.info("Successfully invoked lambda and received success message: {}",
                                                 resp.getMessage()))
                .doOnError(err -> updateMatchWithStatus(matchId, NO_ACTION).subscribe());
    }

    private Mono<Match> updateMatchWithStatus(String matchId, DemoStatus demoStatus) {
        return this.matchService.updateMatchDemoDetailsStatus(matchId, demoStatus)
                .doOnSuccess(s -> logger.info("Updated match [{}] with demo status [{}]", matchId, demoStatus.name()));
    }
}
