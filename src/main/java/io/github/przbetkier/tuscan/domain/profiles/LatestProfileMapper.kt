package io.github.przbetkier.tuscan.domain.profiles

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse
import java.time.Instant

class LatestProfileMapper {

    companion object {
        fun mapAndUpdate(profile: LatestProfile, instant: Instant): LatestProfile {
            return profile.let {
                LatestProfile(it.nickname,
                        it.avatarUrl,
                        it.level,
                        it.elo,
                        it.kdRatio,
                        instant)
            }
        }
        fun mapToNewFromResponses(response: PlayerDetailsResponse, statsResponse: PlayerCsgoStatsResponse,
                                  instant: Instant): LatestProfile {
            return response.let {
                LatestProfile(it.nickname,
                        it.avatarUrl,
                        it.gameDetails.level,
                        it.gameDetails.faceitElo,
                        statsResponse.overallStats.kdRatio,
                        instant)
            }
        }
    }
}
