package io.github.przbetkier.tuscan.adapter.api;

import io.github.przbetkier.tuscan.adapter.api.response.LatestProfilesResponse;
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileRepository;
import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
@RequestMapping("/tuscan-api/latest-profiles")
@RestController
public class LatestProfilesEndpoint {

    private final LatestProfileRepository latestProfilesRespository;
    private final LatestProfileService latestProfileService;

    public LatestProfilesEndpoint(LatestProfileRepository latestProfilesRespository,
                                  LatestProfileService latestProfileService) {
        this.latestProfilesRespository = latestProfilesRespository;
        this.latestProfileService = latestProfileService;
    }

    @GetMapping
    public LatestProfilesResponse getLatestProfiles() {
        return new LatestProfilesResponse(latestProfilesRespository.findAllByOrderByCreatedOnDesc());
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void getLatestProfiles(@RequestBody String nickname) {
        latestProfileService.save(nickname);
    }

}
