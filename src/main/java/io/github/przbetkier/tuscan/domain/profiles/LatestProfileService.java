package io.github.przbetkier.tuscan.domain.profiles;

import io.github.przbetkier.tuscan.adapter.api.request.LatestProfileRequest;
import io.github.przbetkier.tuscan.supplier.InstantSupplier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LatestProfileService {

    private final InstantSupplier instantSupplier;
    private final LatestProfileRepository latestProfileRepository;

    public LatestProfileService(InstantSupplier instantSupplier,
                                LatestProfileRepository latestProfileRepository) {

        this.instantSupplier = instantSupplier;
        this.latestProfileRepository = latestProfileRepository;
    }

    public Flux<LatestProfile> findLatestProfiles() {
        return latestProfileRepository.findTop4ByOrderByCreatedOnDesc();
    }

    public Mono<LatestProfile> save(LatestProfileRequest request) {
        return Mono.just(request)
                .map(r -> LatestProfileMapper.Companion.mapAndUpdate(r, instantSupplier.get()))
                .flatMap(latestProfileRepository::save);
    }
}
