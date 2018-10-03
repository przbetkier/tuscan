package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.common.SampleMatchStatsDto
import spock.lang.Specification

class MatchFullDetailsMapperTest extends Specification {

    def "should map dto to response"() {
        given:
        def playerId = "playerId"
        def stubDto = SampleMatchStatsDto.simple(playerId)

        when:
        def result = MatchFullDetailsMapper.map(stubDto, playerId)

        then:
        noExceptionThrown()
        result.map == stubDto.matchFullDetails[0].roundStatsDto.map
        result.matchId == stubDto.matchFullDetails[0].matchId
        result.result == MatchResult.WIN
        result.winnerTeam == stubDto.matchFullDetails[0].roundStatsDto.winnerTeamId
    }
}
