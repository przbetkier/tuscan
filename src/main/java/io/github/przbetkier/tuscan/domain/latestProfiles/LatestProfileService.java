package io.github.przbetkier.tuscan.domain.latestProfiles;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.client.player.FaceitPlayerClient;
import io.github.przbetkier.tuscan.supplier.LocalDateTimeSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileMapper.mapAndUpdate;
import static io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileMapper.mapToNewFromResponses;

@Service
public class LatestProfileService {

    private static final Logger logger = LoggerFactory.getLogger(LatestProfileService.class);

    private final FaceitPlayerClient client;
    private final LocalDateTimeSupplier localDateTimeSupplier;
    private final LatestProfileRepository latestProfileRepository;

    public LatestProfileService(FaceitPlayerClient client, LocalDateTimeSupplier localDateTimeSupplier,
                                LatestProfileRepository latestProfileRepository) {
        this.client = client;
        this.localDateTimeSupplier = localDateTimeSupplier;
        this.latestProfileRepository = latestProfileRepository;
    }

    public Mono<LatestProfile> save(String nickname) {

        return latestProfileRepository.findById(nickname).map(p -> {
            logger.info("Player {} found in DB, fetching...", nickname);
            LatestProfile updated = mapAndUpdate(p, localDateTimeSupplier.get());
            return latestProfileRepository.save(updated);
        }).switchIfEmpty(Mono.fromCallable(() -> {
            LatestProfile profile = fetch(nickname);
            return latestProfileRepository.save(profile);
        })).flatMap(prof -> prof);
    }

    public Flux<LatestProfile> findLatestProfiles() {
        return latestProfileRepository.findTop4ByOrderByCreatedOnDesc();
    }

    private LatestProfile fetch(String nickname) {
        PlayerDetailsResponse response = client.getPlayerDetails(nickname);
        PlayerCsgoStatsResponse statsResponse = client.getPlayerCsgoStats(response.getPlayerId());
        return mapToNewFromResponses(response, statsResponse, localDateTimeSupplier.get());
    }


}
