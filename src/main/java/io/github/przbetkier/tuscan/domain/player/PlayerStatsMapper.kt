package io.github.przbetkier.tuscan.domain.player

import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MapStats
import io.github.przbetkier.tuscan.adapter.api.response.dto.OverallStats
import io.github.przbetkier.tuscan.client.player.PlayerStats
import io.github.przbetkier.tuscan.client.player.Segment
import io.github.przbetkier.tuscan.domain.CsgoMap
import io.github.przbetkier.tuscan.domain.CsgoMap.isInMapPool

class PlayerStatsMapper {

    companion object {
        fun map(playerStats: PlayerStats): PlayerCsgoStatsResponse {
            val mapStats = mapToCsgoMapStats(playerStats)
            return PlayerCsgoStatsResponse(mapToOverallStats(playerStats, mapStats), mapStats)
        }

        private fun mapToOverallStats(playerStats: PlayerStats, mapStats: List<MapStats>): OverallStats {
            return playerStats.let {
                OverallStats(it.lifetime.headshotPercentage.toBigDecimal(),
                        it.lifetime.kdRatio.toBigDecimal(),
                        format(it.lifetime.matches),
                        it.lifetime.winRate.toInt(),
                        PlayerPerformanceMapper.map(mapStats),
                        it.lifetime.currentWinStreak,
                        it.lifetime.longestWinStreak)
            }
        }

        private fun mapToCsgoMapStats(playerStats: PlayerStats): List<MapStats> {
            val mapStats = playerStats.segments
                    .filter { (name, _, mode) -> mode == "5v5" && isInMapPool(name) }
                    .map { segment -> (mapSegmentToMapStats(segment)) }
            return PlayerPerformanceMapper.orderFromHighestKdRatio(mapStats)
        }

        private fun mapSegmentToMapStats(segment: Segment): MapStats {
            return segment.let {
                MapStats(CsgoMap.valueOf(it.name.toUpperCase()),
                        format(it.mapStatistics.matches),
                        it.mapStatistics.kdRatio.toBigDecimal(),
                        format(it.mapStatistics.wins),
                        it.mapStatistics.winPercentage.toInt(),
                        it.mapStatistics.hsPercentage.toInt(),
                        it.mapStatistics.averageKills.toBigDecimal(),
                        format(it.mapStatistics.tripleKills),
                        format(it.mapStatistics.quadroKills),
                        format(it.mapStatistics.pentaKills))
            }
        }

        private fun format(number: String): Int {
            return number.replace(",", "").toInt()
        }
    }
}
