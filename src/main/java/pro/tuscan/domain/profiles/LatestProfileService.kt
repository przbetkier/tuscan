package pro.tuscan.domain.profiles

import org.springframework.stereotype.Service
import pro.tuscan.adapter.api.LatestProfileRequest
import pro.tuscan.domain.profiles.LatestProfileMapper.Companion.mapAndUpdate
import pro.tuscan.supplier.InstantSupplier
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class LatestProfileService(private val instantSupplier: InstantSupplier,
                           private val latestProfileRepository: LatestProfileRepository) {

    fun findLatestProfiles(): Flux<LatestProfile> =
            latestProfileRepository.findTop6ByOrderByCreatedOnDesc()

    fun save(request: LatestProfileRequest): Mono<LatestProfile> =
            Mono.just(request)
                    .map { req -> mapAndUpdate(req, instantSupplier.get()) }
                    .flatMap { entity -> latestProfileRepository.save(entity) }
}
