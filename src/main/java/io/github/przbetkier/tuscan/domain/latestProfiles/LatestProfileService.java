package io.github.przbetkier.tuscan.domain.latestProfiles;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;
import io.github.przbetkier.tuscan.common.supplier.LocalDateTimeSupplier;
import io.github.przbetkier.tuscan.domain.player.FaceitPlayerClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            repository.save(new LatestProfile(
                    profile.get().getNickname(),
                    profile.get().getAvatarUrl(),
                    profile.get().getLevel(),
                    profile.get().getElo(),
                    profile.get().getKdRatio(),
                    LocalDateTime.now()));
        } else {
            PlayerDetailsResponse response = client.getPlayerDetails(nickname);
            PlayerCsgoStatsResponse statsResponse = client.getPlayerCsgoStats(response.getPlayerId());
            repository.save(new LatestProfile(
                    response.getNickname(),
                    response.getAvatarUrl(),
                    response.getGameDetails().getLevel(),
                    response.getGameDetails().getFaceitElo(),
                    statsResponse.getOverallStats().getKdRatio(),
                    LocalDateTime.now()
            ));
        }

        List<LatestProfile> profiles = repository.findAllByOrderByCreatedOnDesc();
        if (profiles.size() > 5) {
            repository.deleteAll();
            repository.saveAll(profiles.subList(0, 4));
        }
    }
}
