package pro.tuscan.domain.profiles;

import pro.tuscan.adapter.api.request.LatestProfileRequest;
import pro.tuscan.supplier.InstantSupplier;
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
        return latestProfileRepository.findTop6ByOrderByCreatedOnDesc();
    }

    public Mono<LatestProfile> save(LatestProfileRequest request) {
        return Mono.just(request)
                .map(r -> LatestProfileMapper.Companion.mapAndUpdate(r, instantSupplier.get()))
                .flatMap(latestProfileRepository::save);
    }
}
