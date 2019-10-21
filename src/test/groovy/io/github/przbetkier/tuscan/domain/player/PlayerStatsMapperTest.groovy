package io.github.przbetkier.tuscan.domain.player

import io.github.przbetkier.tuscan.common.SamplePlayerStats
import io.github.przbetkier.tuscan.domain.CsgoMap
import spock.lang.Specification

import static io.github.przbetkier.tuscan.common.SamplePlayerStats.simple

class PlayerStatsMapperTest extends Specification {

    def "should map to player stats"() {
        given:
        def kdRatio = "1.40"
        def matches = "1,999"
        def playerStats = simple(kdRatio, matches)

        when:
        def result = PlayerStatsMapper.@Companion.map(playerStats)

        then:
        with(result) {
            overallStats.performance.bestTeamPerformance.map.name == SamplePlayerStats.BEST_TEAM
            overallStats.performance.bestSoloPerformance.map.name == SamplePlayerStats.BEST_SOLO
            overallStats.performance.worstSoloPerformance.map.name == SamplePlayerStats.WORST_SOLO
            overallStats.performance.worstTeamPerformance.map.name == SamplePlayerStats.WORST_TEAM
            overallStats.kdRatio == 1.40
            overallStats.matches == 1999
            mapStats.find({ it.csgoMap == CsgoMap.DE_MIRAGE }).kdRatio == playerStats.segments.find({
                it.name == "de_mirage"
            }).mapStatistics.kdRatio.toDouble()
        }
    }
}
