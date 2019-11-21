package io.github.przbetkier.tuscan.domain.profiles;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
interface LatestProfileRepository extends ReactiveMongoRepository<LatestProfile, String> {

    Flux<LatestProfile> findTop6ByOrderByCreatedOnDesc();

}
