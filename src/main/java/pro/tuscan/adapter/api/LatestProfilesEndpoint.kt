package pro.tuscan.adapter.api

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pro.tuscan.domain.profiles.LatestProfile
import pro.tuscan.domain.profiles.LatestProfileService
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal

@RequestMapping("/tuscan-api/latest-profiles")
@RestController
class LatestProfilesEndpoint(private val latestProfileService: LatestProfileService) {

    private val emitter: EmitterProcessor<ServerSentEvent<LatestProfile>> = EmitterProcessor.create(false)

    @ResponseStatus(CREATED)
    @PostMapping
    fun saveProfile(@RequestBody request: LatestProfileRequest): Mono<LatestProfile> =
            latestProfileService.save(request).doOnSuccess { profile ->
                try {
                    emitter.onNext(ServerSentEvent.builder(profile).build())
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }

    @GetMapping
    fun getLatestProfiles(): Flux<LatestProfile> = latestProfileService.findLatestProfiles()

    @GetMapping(value = ["/sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun events(): Flux<ServerSentEvent<LatestProfile>> = emitter
}

data class LatestProfileRequest(val nickname: String,
                                val avatarUrl: String,
                                val level: Number,
                                val elo: Number,
                                val kdRatio: BigDecimal)
