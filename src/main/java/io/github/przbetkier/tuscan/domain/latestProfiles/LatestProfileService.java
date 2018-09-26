package io.github.przbetkier.tuscan.domain.latestProfiles;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.common.supplier.LocalDateTimeSupplier;
import io.github.przbetkier.tuscan.domain.player.FaceitPlayerClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfileMapper.*;

@Service
public class LatestProfileService {

    private final LatestProfileRepository repository;
    private final FaceitPlayerClient client;
    private final LocalDateTimeSupplier localDateTimeSupplier;

    public LatestProfileService(LatestProfileRepository repository,
                                FaceitPlayerClient client,
                                LocalDateTimeSupplier localDateTimeSupplier) {
        this.repository = repository;
        this.client = client;
        this.localDateTimeSupplier = localDateTimeSupplier;
    }

    public void save(String nickname) {
        Optional<LatestProfile> profile = repository.findById(nickname);

        if (profile.isPresent()) {
            LatestProfile updatedProfile = mapAndUpdate(profile.get(), localDateTimeSupplier.get());
            repository.save(updatedProfile);
        } else {
            PlayerDetailsResponse response = client.getPlayerDetails(nickname);
            PlayerCsgoStatsResponse statsResponse = client.getPlayerCsgoStats(response.getPlayerId());
            LatestProfile newProfile = mapToNewFromResponses(response, statsResponse, localDateTimeSupplier.get());
            repository.save(newProfile);
        }
        trimProfiles();
    }

    private void trimProfiles() {
        List<LatestProfile> profiles = repository.findAllByOrderByCreatedOnDesc();
        if (profiles.size() > 5) {
            repository.deleteAll();
            repository.saveAll(profiles.subList(0, 4));
        }
    }
}
