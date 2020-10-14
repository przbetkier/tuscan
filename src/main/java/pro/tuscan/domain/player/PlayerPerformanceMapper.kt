package pro.tuscan.domain.player

import pro.tuscan.adapter.api.response.dto.MapStats
import pro.tuscan.adapter.api.response.dto.Performance
import pro.tuscan.adapter.api.response.dto.SoloPerformance
import pro.tuscan.adapter.api.response.dto.TeamPerformance

class PlayerPerformanceMapper {

    companion object {
        fun map(mapStats: List<MapStats>): Performance {
            return Performance(
                    bestSoloPerformance = getExtremeByKdRatio(mapStats, true)
                            .let { SoloPerformance(it.csgoMap, it.kdRatio) },
                    bestTeamPerformance = getExtremeByWinRate(mapStats, true)
                            .let { TeamPerformance(it.csgoMap, it.winPercentage) },
                    worstSoloPerformance = getExtremeByKdRatio(mapStats, false)
                            .let { SoloPerformance(it.csgoMap, it.kdRatio) },
                    worstTeamPerformance = getExtremeByWinRate(mapStats, false)
                            .let { TeamPerformance(it.csgoMap, it.winPercentage) })
        }

        fun getExtremeByKdRatio(mapStats: List<MapStats>, reversed: Boolean) =
                mapStats.sortedBy { it.kdRatio }
                        .let { if (reversed) it.reversed() else it }
                        .first()

        private fun getExtremeByWinRate(mapStats: List<MapStats>, reversed: Boolean) =
                mapStats.sortedBy { it.winPercentage }
                        .let { if (reversed) it.reversed() else it }
                        .first()
    }
}
