package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.LatestProfilesResponse;
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfile;
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileRepository;
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RequestMapping("/tuscan-api/latest-profiles")
@RestController
class LatestProfilesEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(LatestProfilesEndpoint.class);

    private final List<SseEmitter> emitters = new ArrayList<>();
    private final LatestProfileRepository latestProfilesRespository;
    private final LatestProfileService latestProfileService;
    private final ApplicationEventPublisher eventPublisher;


    LatestProfilesEndpoint(LatestProfileRepository latestProfilesRespository, LatestProfileService latestProfileService,
                           ApplicationEventPublisher eventPublisher) {
        this.latestProfilesRespository = latestProfilesRespository;
        this.latestProfileService = latestProfileService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public LatestProfilesResponse getLatestProfiles() {
        return new LatestProfilesResponse(latestProfilesRespository.findAllByOrderByCreatedOnDesc());
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void getLatestProfiles(@RequestBody String nickname) {
        LatestProfile prof = latestProfileService.save(nickname);
        eventPublisher.publishEvent(prof);
    }

    @GetMapping(value = "/sse", produces = TEXT_EVENT_STREAM_VALUE)
    public SseEmitter events() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        logger.info("I registered new emitter {}", emitter.toString());
        emitter.onCompletion(() -> {
            logger.info("I did my work...");
            emitters.remove(emitter);
        });
        emitter.onError(throwable -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    @EventListener
    public void onNotification(LatestProfile notification) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
            this.emitters.forEach(emitter -> {
                try {
                    emitter.send(notification);
                    logger.info("Hello from event listener");
                } catch (Exception e) {
                    deadEmitters.add(emitter);
                }
            });
            this.emitters.remove(deadEmitters);
    }
}
