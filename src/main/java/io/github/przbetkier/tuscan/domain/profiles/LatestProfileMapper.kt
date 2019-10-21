package io.github.przbetkier.tuscan.domain.profiles

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.adapter.api.response.PlayerDetailsResponse
import java.time.LocalDateTime

class LatestProfileMapper {

    companion object {
        fun mapAndUpdate(profile: LatestProfile, dateTime: LocalDateTime): LatestProfile {
            return profile.let {
                LatestProfile(it.nickname,
                        it.avatarUrl,
                        it.level,
                        it.elo,
                        it.kdRatio,
                        dateTime)
            }
        }
        fun mapToNewFromResponses(response: PlayerDetailsResponse, statsResponse: PlayerCsgoStatsResponse,
                                  dateTime: LocalDateTime): LatestProfile {
            return response.let {
                LatestProfile(it.nickname,
                        it.avatarUrl,
                        it.gameDetails.level,
                        it.gameDetails.faceitElo,
                        statsResponse.overallStats.kdRatio,
                        dateTime)
            }
        }
    }
}
