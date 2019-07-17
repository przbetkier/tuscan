package io.github.przbetkier.tuscan.domain.latestProfiles;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LatestProfileRepository extends ReactiveMongoRepository<LatestProfile, String> {

    Flux<LatestProfile> findTop4ByOrderByCreatedOnDesc();

}
