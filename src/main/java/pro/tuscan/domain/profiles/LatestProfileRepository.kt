package pro.tuscan.domain.profiles

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface LatestProfileRepository: ReactiveMongoRepository<LatestProfile, String> {
    fun findTop6ByOrderByCreatedOnDesc(): Flux<LatestProfile>
}
