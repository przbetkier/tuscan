package io.github.przbetkier.tuscan.domain.player

import io.github.przbetkier.tuscan.common.SamplePlayerStats
import io.github.przbetkier.tuscan.domain.CsgoMap
import spock.lang.Specification

class PlayerStatsMapperTest extends Specification {

    def "should map to player stats"() {
        given:
        def kdRatio = "1.40"
        def matches = "1,999"
        def playerStats = SamplePlayerStats.simple(kdRatio, matches)

        when:
        def result = PlayerStatsMapper.map(playerStats)

        then:
        result.overallStats.performance.bestTeamPerformance.map.name == SamplePlayerStats.BEST_TEAM
        result.overallStats.performance.bestSoloPerformance.map.name == SamplePlayerStats.BEST_SOLO
        result.overallStats.kdRatio == 1.40
        result.overallStats.matches == 1999
        result.mapStats.find({ it.csgoMap == CsgoMap.DE_MIRAGE }).kdRatio == playerStats.segments.find({it.name == "de_mirage"}).mapStatistics.kdRatio.toDouble()
    }
}
