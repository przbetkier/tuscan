package io.github.przbetkier.tuscan.domain.profiles;

import io.github.przbetkier.tuscan.client.player.FaceitPlayerClient;
import io.github.przbetkier.tuscan.supplier.LocalDateTimeSupplier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LatestProfileService {

    private final FaceitPlayerClient client;
    private final LocalDateTimeSupplier localDateTimeSupplier;
    private final LatestProfileRepository latestProfileRepository;

    public LatestProfileService(FaceitPlayerClient client, LocalDateTimeSupplier localDateTimeSupplier,
                                LatestProfileRepository latestProfileRepository) {
        this.client = client;
        this.localDateTimeSupplier = localDateTimeSupplier;
        this.latestProfileRepository = latestProfileRepository;
    }

    public Flux<LatestProfile> findLatestProfiles() {
        return latestProfileRepository.findTop4ByOrderByCreatedOnDesc();
    }

    private Mono<LatestProfile> getLatestProfile(String nickname) {
        return latestProfileRepository.findById(nickname).switchIfEmpty(Mono.defer(() -> fetch(nickname)));
    }

    public Mono<LatestProfile> save(String nickname) {
        return getLatestProfile(nickname).map(p -> LatestProfileMapper.Companion.mapAndUpdate(p, localDateTimeSupplier.get()))
                .flatMap(latestProfileRepository::save);
    }

    private Mono<LatestProfile> fetch(String nickname) {
        return client.getPlayerDetails(nickname)
                .flatMap(response -> client.getPlayerCsgoStats(response.getPlayerId())
                        .map(statsResponse -> LatestProfileMapper.Companion.mapToNewFromResponses(response,
                                                                    statsResponse,
                                                                    localDateTimeSupplier.get())));
    }
}
