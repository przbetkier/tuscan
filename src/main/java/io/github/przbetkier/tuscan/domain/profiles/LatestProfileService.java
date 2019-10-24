package io.github.przbetkier.tuscan.domain.profiles;

import io.github.przbetkier.tuscan.client.player.FaceitPlayerClient;
import io.github.przbetkier.tuscan.supplier.InstantSupplier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LatestProfileService {

    private final FaceitPlayerClient client;
    private final InstantSupplier instantSupplier;
    private final LatestProfileRepository latestProfileRepository;

    public LatestProfileService(FaceitPlayerClient client, InstantSupplier instantSupplier,
                                LatestProfileRepository latestProfileRepository) {
        this.client = client;
        this.instantSupplier = instantSupplier;
        this.latestProfileRepository = latestProfileRepository;
    }

    public Flux<LatestProfile> findLatestProfiles() {
        return latestProfileRepository.findTop4ByOrderByCreatedOnDesc();
    }

    private Mono<LatestProfile> getLatestProfile(String nickname) {
        return latestProfileRepository.findById(nickname).switchIfEmpty(Mono.defer(() -> fetch(nickname)));
    }

    public Mono<LatestProfile> save(String nickname) {
        return getLatestProfile(nickname).map(p -> LatestProfileMapper.Companion.mapAndUpdate(p, instantSupplier.get()))
                .flatMap(latestProfileRepository::save);
    }

    private Mono<LatestProfile> fetch(String nickname) {
        return client.getPlayerDetails(nickname)
                .flatMap(response -> client.getPlayerCsgoStats(response.getPlayerId())
                        .map(statsResponse -> LatestProfileMapper.Companion.mapToNewFromResponses(response,
                                                                                                  statsResponse,
                                                                                                  instantSupplier.get())));
    }
}
