package pro.tuscan.adapter.api.response.dto

import pro.tuscan.domain.CsgoMap
import java.math.BigDecimal

data class Performance(val bestSoloPerformance: SoloPerformance,
                       val bestTeamPerformance: TeamPerformance,
                       val worstSoloPerformance: SoloPerformance,
                       val worstTeamPerformance: TeamPerformance)

class SoloPerformance(val map: CsgoMap, val kdRatio: BigDecimal)

class TeamPerformance(val map: CsgoMap, val winRate: Int)
