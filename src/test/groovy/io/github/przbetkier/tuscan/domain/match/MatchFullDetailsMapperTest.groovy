package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.common.SampleMatchDemoDto
import io.github.przbetkier.tuscan.common.SampleMatchStatsDto
import spock.lang.Specification

class MatchFullDetailsMapperTest extends Specification {

    def "should map dto to response"() {
        given:
        def playerId = "playerId"
        def stubDto = SampleMatchStatsDto.simple(playerId)
        def demoDto = SampleMatchDemoDto.simple()
        def firstPlayerStats = stubDto.matchFullDetails.first().teams.first().players.first().playerStats

        when:
        def result = MatchFullDetailsMapper.@Companion.map(stubDto, playerId, demoDto)

        then:
        noExceptionThrown()
        result.map == stubDto.matchFullDetails[0].roundStatsDto.map
        result.matchId == stubDto.matchFullDetails[0].matchId
        result.result == MatchResult.WIN
        result.score == stubDto.matchFullDetails[0].roundStatsDto.score
        result.roundsCount == stubDto.matchFullDetails[0].roundStatsDto.roundsCount.toInteger()
        result.winnerTeam == stubDto.matchFullDetails[0].roundStatsDto.winnerTeamId
        result.teams[0].players[0].nickname == stubDto.matchFullDetails[0].teams[0].players[0].nickname
        result.teams.first().teamName == stubDto.matchFullDetails.first().teams.first().teamStats.teamName
        result.demoUrl == demoDto.urls.first()

        with(result.teams[0].players[0].playerStats) {
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
    }

    def "should return loss result when player did not play in winner team"() {
        given:
        def playerId = "playerId"
        def stubDto = SampleMatchStatsDto.forPlayerWhoLost(playerId)
        def demoDto = SampleMatchDemoDto.simple()

        when:
        def response = MatchFullDetailsMapper.@Companion.map(stubDto, playerId, demoDto)

        then:
        response.result == MatchResult.LOSS
    }

    def 'should map K/D with kills number when player has no deaths'() {
        given:
        def playerId = "playerId"
        def inputKills = '5'
        def inputKdRatio = '0.0'
        def stubDto = SampleMatchStatsDto.zeroKdFirstPlayer(playerId, inputKills, inputKdRatio)
        def demoDto = SampleMatchDemoDto.simple()

        when:
        def result = MatchFullDetailsMapper.@Companion.map(stubDto, playerId, demoDto)

        then:
        with(result.teams[0].players[0].playerStats) {
            kdRatio == BigDecimal.valueOf(5.0)
            kills == 5
        }
    }
}
