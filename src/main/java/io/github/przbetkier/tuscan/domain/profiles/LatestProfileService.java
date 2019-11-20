package io.github.przbetkier.tuscan.domain.profiles;

import io.github.przbetkier.tuscan.adapter.api.request.LatestProfileRequest;
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
        return latestProfileRepository.findById(nickname)
                .switchIfEmpty(Mono.defer(() -> fetch(nickname)));
    }

    public Mono<LatestProfile> save(LatestProfileRequest request) {
        return getLatestProfile(request.getNickname())
                .map(p -> LatestProfileMapper.Companion.mapAndUpdate(request, instantSupplier.get()))
                .flatMap(latestProfileRepository::save);
    }

    private Mono<LatestProfile> fetch(String nickname) {
        return client.getPlayerDetails(nickname)
                .flatMap(response -> client.getPlayerCsgoStats(response.getPlayerId())
                        .map(statsResponse -> LatestProfileMapper.Companion.mapToNewFromResponses(response, statsResponse,
                                instantSupplier.get())));
    }
}
