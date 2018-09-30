package io.github.przbetkier.tuscan.domain.player

import io.github.przbetkier.tuscan.domain.player.dto.MatchHistoryDto
import io.github.przbetkier.tuscan.domain.player.dto.PlayerHistoryDto
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDateTime

class PlayerHistoryMapperTest extends Specification {

    def "should map dto with 50 matches to last 20 matches player history response"() {
        given:
        def listOf50MatchesHistory = generateMatchHistoryDto(1200, 50)
        def playerHistoryDto = new PlayerHistoryDto(listOf50MatchesHistory)

        when:
        def result = PlayerHistoryMapper.map(playerHistoryDto.getMatchHistoryDtoList())

        then:
        result.matchHistory.size() == 20
        result.matchHistory[0].date == LocalDateTime.ofInstant(Instant.ofEpochMilli(listOf50MatchesHistory[0].date), TimeZone.getDefault().toZoneId())
        result.matchHistory[0].eloGain == result.matchHistory[0].elo - result.matchHistory[1].elo
        result.matchHistory[1].eloGain == result.matchHistory[1].elo - result.matchHistory[2].elo
        result.matchHistory[19].eloGain == result.matchHistory[19].elo - Integer.valueOf(listOf50MatchesHistory.get(20).elo)
    }

    def "should map dto with 50 matches to last 20 without elo"() {
        given:
        def listOf50MatchesHistory = generateMatchHistoryDto(null, 50)
        def playerHistoryDto = new PlayerHistoryDto(listOf50MatchesHistory)

        when:
        def result = PlayerHistoryMapper.map(playerHistoryDto.getMatchHistoryDtoList())

        then:
        result.matchHistory.size() == 20
        for (int i = 0; i < result.matchHistory.size(); i++) {
            result.matchHistory[i].elo == 0 && result.matchHistory[i].eloGain == 0
        }
    }

    def "should map dto with less than 20 matches to last matches history"() {
        given:
        def matchesList = generateMatchHistoryDto(1025, 10)
        def playerHistoryDto = new PlayerHistoryDto(matchesList)

        when:
        def result = PlayerHistoryMapper.map(playerHistoryDto.getMatchHistoryDtoList())

        then:
        result.matchHistory.size() == 10
        result.matchHistory[matchesList.size() - 1].eloGain == 25
    }

    static generateMatchHistoryDto(Integer elo, int matches) {
        List<MatchHistoryDto> matchesList = new ArrayList<>()

        def eloPoints

        if (elo != null) {
            eloPoints = elo.toString()
        } else {
            eloPoints = null
        }

        for (int i = 0; i < matches; i++) {
            def match = new MatchHistoryDto(
                    eloPoints,
                    "match-$i",
                    12345,
                    "5v5"
            )
            matchesList.add(match)
            if (elo) {
                elo++
            }
        }
        return matchesList
    }
}
