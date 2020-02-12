package pro.tuscan.adapter.api;

import pro.tuscan.adapter.api.request.LatestProfileRequest;
import pro.tuscan.domain.profiles.LatestProfile;
import pro.tuscan.domain.profiles.LatestProfileService;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RequestMapping("/tuscan-api/latest-profiles")
@RestController
@Timed("endpoint.latestProfiles")
class LatestProfilesEndpoint {

    private final EmitterProcessor<ServerSentEvent<LatestProfile>> emitter;
    private final LatestProfileService latestProfileService;

    LatestProfilesEndpoint(LatestProfileService latestProfileService) {
        this.emitter = EmitterProcessor.create(false);
        this.latestProfileService = latestProfileService;
    }

    @GetMapping
    public Flux<LatestProfile> getLatestProfiles() {
        return latestProfileService.findLatestProfiles();
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public Mono<LatestProfile> saveProfile(@RequestBody LatestProfileRequest request) {

        return latestProfileService.save(request).doOnSuccess(profile -> {
            try {
                emitter.onNext(ServerSentEvent.builder(profile).build());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @GetMapping(value = "/sse", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<LatestProfile>> events() {
        return getEvents();
    }

    private Flux<ServerSentEvent<LatestProfile>> getEvents() {
        return emitter;
    }
}
