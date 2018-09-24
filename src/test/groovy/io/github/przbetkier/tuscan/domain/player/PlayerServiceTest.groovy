package io.github.przbetkier.tuscan.domain.player

import io.github.przbetkier.tuscan.common.response.SamplePlayerCsgoStats
import io.github.przbetkier.tuscan.common.response.SamplePlayerDetailsResponse
import io.github.przbetkier.tuscan.common.response.SamplePlayerHistoryResponse
import spock.lang.Specification
import spock.lang.Subject

class PlayerServiceTest extends Specification {

    FaceitPlayerClient faceitPlayerClient = Mock(FaceitPlayerClient)
    PlayerHistoryClient playerHistoryClient = Mock(PlayerHistoryClient)

    @Subject
    PlayerService playerService = new PlayerService(faceitPlayerClient, playerHistoryClient)

    def "should return player details response"() {
        given:
        def nickname = "player"
        def response = SamplePlayerDetailsResponse.simple()
        faceitPlayerClient.getPlayerDetails(nickname) >> response

        when:
        def result = playerService.getPlayerDetails(nickname)

        then:
        result.nickname == response.nickname
        result.playerId == response.playerId
        result.avatarUrl == response.avatarUrl
        result.country == response.country
        result.gameDetails.faceitElo == response.gameDetails.faceitElo
        result.gameDetails.level == response.gameDetails.level
    }

    def "should return player csgo stats"() {
        given:
        def playerId = "playerId-1"
        def response = SamplePlayerCsgoStats.simple()

        faceitPlayerClient.getPlayerCsgoStats(playerId) >> response

        when:
        def result = playerService.getCsgoStats(playerId)

        then:
        result.overallStats.kdRatio == response.overallStats.kdRatio
        result.overallStats.winPercentage == response.overallStats.winPercentage
        result.overallStats.matches == response.overallStats.matches
        result.overallStats.highestSoloPerformanceOn == response.overallStats.highestSoloPerformanceOn
        result.overallStats.highestTeamPerformanceOn == response.overallStats.highestTeamPerformanceOn
    }

    def "should return player history"() {
        given:
        def playerId = "playerId-1"
        def response = SamplePlayerHistoryResponse.simple()

        playerHistoryClient.getPlayerHistory(playerId) >> response

        when:
        def result = playerService.getPlayerHistory(playerId)

        then:
        result.matchHistory.size() == response.matchHistory.size()
        for (match in result.matchHistory) {
            def comparedTo = response.matchHistory.find({ it.matchId == match.matchId })
            match.eloGain == comparedTo.eloGain
            match.elo == comparedTo.elo
            match.date == comparedTo.date
        }
    }
}
