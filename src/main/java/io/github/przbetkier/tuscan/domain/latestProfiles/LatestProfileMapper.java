package io.github.przbetkier.tuscan.domain.latestProfiles;

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse;
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse;

import java.time.LocalDateTime;

class LatestProfileMapper {

    private LatestProfileMapper() {
    }

    static LatestProfile mapAndUpdate(LatestProfile profile, LocalDateTime dateTime) {
        return new LatestProfile(profile.getNickname(),
                                 profile.getAvatarUrl(),
                                 profile.getLevel(),
                                 profile.getElo(),
                                 profile.getKdRatio(),
                                 dateTime);
    }

    static LatestProfile mapToNewFromResponses(PlayerDetailsResponse response, PlayerCsgoStatsResponse statsResponse,
                                               LocalDateTime dateTime) {
        return new LatestProfile(response.getNickname(),
                                 response.getAvatarUrl(),
                                 response.getGameDetails().getLevel(),
                                 response.getGameDetails().getFaceitElo(),
                                 statsResponse.getOverallStats().getKdRatio(),
                                 dateTime);
    }
}
