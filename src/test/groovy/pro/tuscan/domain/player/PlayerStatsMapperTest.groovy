package pro.tuscan.domain.player


import spock.lang.Specification

import static pro.tuscan.common.SamplePlayerStats.BEST_SOLO
import static pro.tuscan.common.SamplePlayerStats.BEST_TEAM
import static pro.tuscan.common.SamplePlayerStats.WORST_SOLO
import static pro.tuscan.common.SamplePlayerStats.WORST_TEAM
import static pro.tuscan.common.SamplePlayerStats.simple
import static pro.tuscan.domain.CsgoMap.DE_MIRAGE

class PlayerStatsMapperTest extends Specification {

    def "should map to player stats"() {
        given:
        def kdRatio = "1.40"
        def matches = "1,999"
        def playerStats = simple(kdRatio, matches)

        when:
        def result = PlayerStatsMapper.map(playerStats)

        then:
        with(result) {
            with(overallStats.performance) {
                bestTeamPerformance.map.name == BEST_TEAM
                bestSoloPerformance.map.name == BEST_SOLO
                worstSoloPerformance.map.name == WORST_SOLO
                worstTeamPerformance.map.name == WORST_TEAM
            }
            overallStats.kdRatio == 1.40
            overallStats.matches == 1999
            mapStats.find { it.csgoMap == DE_MIRAGE }.kdRatio == playerStats.segments.find({
                it.name == "de_mirage"
            }).mapStatistics.kdRatio.toDouble()
        }
    }
}
