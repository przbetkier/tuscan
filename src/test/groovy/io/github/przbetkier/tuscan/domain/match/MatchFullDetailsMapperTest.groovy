package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.common.SampleMatchStatsDto
import spock.lang.Specification

class MatchFullDetailsMapperTest extends Specification {

    def "should map dto to response"() {
        given:
        def playerId = "playerId"
        def stubDto = SampleMatchStatsDto.simple(playerId)
        def firstPlayerStats = stubDto.matchFullDetails[0].teams[0].players[0].playerStats

        when:
        def result = MatchFullDetailsMapper.map(stubDto, playerId)

        then:
        noExceptionThrown()
        result.map == stubDto.matchFullDetails[0].roundStatsDto.map
        result.matchId == stubDto.matchFullDetails[0].matchId
        result.result == MatchResult.WIN
        result.score == stubDto.matchFullDetails[0].roundStatsDto.score
        result.roundsCount == stubDto.matchFullDetails[0].roundStatsDto.roundsCount.toInteger()
        result.winnerTeam == stubDto.matchFullDetails[0].roundStatsDto.winnerTeamId
        result.teams[0].players[0].nickname == stubDto.matchFullDetails[0].teams[0].players[0].nickname
        with (result.teams[0].players[0].playerStats) {
            kills == firstPlayerStats.kills.toInteger()
            assists == firstPlayerStats.assists.toInteger()
            deaths == firstPlayerStats.deaths.toInteger()
            headshots == firstPlayerStats.headshots.toInteger()
            headshotPercentage == firstPlayerStats.headshotPercentage.toInteger()
            kdRatio == firstPlayerStats.kdRatio.toBigDecimal()
            krRatio == firstPlayerStats.krRatio.toBigDecimal()
            mvps == firstPlayerStats.mvps.toInteger()
            tripleKills == firstPlayerStats.tripleKills.toInteger()
            quadroKills == firstPlayerStats.quadroKills.toInteger()
            pentaKills == firstPlayerStats.pentaKills.toInteger()
        }

        with(result.teams[0].teamStats) {
            headshotAvg == stubDto.matchFullDetails[0].teams[0].teamStats.headshotAvg.toBigDecimal()
            teamName == stubDto.matchFullDetails[0].teams[0].teamStats.teamName
        }
    }
}
