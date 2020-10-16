package pro.tuscan.domain.player

import pro.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import pro.tuscan.adapter.api.response.dto.MapStats
import pro.tuscan.adapter.api.response.dto.OverallStats
import pro.tuscan.client.player.PlayerStats
import pro.tuscan.client.player.Segment
import pro.tuscan.domain.CsgoMap
import pro.tuscan.domain.CsgoMap.isInMapPool

object PlayerStatsMapper {

    @JvmStatic
    fun map(playerStats: PlayerStats): PlayerCsgoStatsResponse {
        val mapStats = mapToCsgoMapStats(playerStats)
        return PlayerCsgoStatsResponse(mapToOverallStats(playerStats, mapStats), mapStats)
    }

    private fun mapToOverallStats(playerStats: PlayerStats, mapStats: List<MapStats>): OverallStats =
            playerStats.lifetime.let { stats ->
                OverallStats(
                        stats.headshotPercentage.toBigDecimal(),
                        stats.kdRatio.toBigDecimal(),
                        format(stats.matches),
                        stats.winRate.toInt(),
                        PlayerPerformanceMapper.map(mapStats),
                        stats.currentWinStreak,
                        stats.longestWinStreak
                )
            }

    private fun mapToCsgoMapStats(playerStats: PlayerStats): List<MapStats> =
            playerStats.segments
                    .filter { (name, _, mode) -> mode == "5v5" && isInMapPool(name) }
                    .map { segment -> (mapSegmentToMapStats(segment)) }
                    .sortedByDescending { it.kdRatio }

    private fun mapSegmentToMapStats(segment: Segment): MapStats =
            segment.let { seg ->
                seg.mapStatistics.let {
                    MapStats(CsgoMap.valueOf(seg.name.toUpperCase()),
                            format(it.matches),
                            it.kdRatio.toBigDecimal(),
                            format(it.wins),
                            it.winPercentage.toInt(),
                            it.hsPercentage.toInt(),
                            it.averageKills.toBigDecimal(),
                            format(it.tripleKills),
                            format(it.quadroKills),
                            format(it.pentaKills))
                }
            }

    private fun format(number: String): Int =
            number.replace(",", "").toInt()
}
