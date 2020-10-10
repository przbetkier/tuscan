package pro.tuscan.domain.player

import pro.tuscan.client.player.FaceitPlayerClient
import pro.tuscan.client.player.PlayerBanClient
import pro.tuscan.client.player.PlayerHistoryClient
import pro.tuscan.common.response.SamplePlayerCsgoStats
import pro.tuscan.common.response.SamplePlayerDetailsResponse
import pro.tuscan.common.response.SamplePlayerHistoryResponse
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject

import static integration.common.MockedPlayer.NICKNAME
import static integration.common.MockedPlayer.PLAYER_ID

class PlayerServiceTest extends Specification {

    FaceitPlayerClient faceitPlayerClient = Mock(FaceitPlayerClient)
    PlayerHistoryClient playerHistoryClient = Mock(PlayerHistoryClient)
    PlayerBanClient playerBanClient = Mock(PlayerBanClient)

    @Subject
    PlayerService playerService = new PlayerService(faceitPlayerClient, playerHistoryClient, playerBanClient)

    def "should return player details response"() {
        given:
        def nickname = NICKNAME
        def response = SamplePlayerDetailsResponse.simple()
        faceitPlayerClient.getPlayerDetails(nickname) >> Mono.just(response)

        when:
        def result = playerService.getPlayerDetails(nickname).block()

        then:
        with(result) {
            nickname == response.nickname
            playerId == response.playerId
            avatarUrl == response.avatarUrl
            country == response.country
            gameDetails.faceitElo == response.gameDetails.faceitElo
            gameDetails.level == response.gameDetails.level
        }
    }

    def "should return player csgo stats"() {
        given:
        def playerId = PLAYER_ID
        def response = SamplePlayerCsgoStats.simple()

        faceitPlayerClient.getPlayerCsgoStats(playerId) >> Mono.just(response)

        when:
        def result = playerService.getCsgoStats(playerId).block()

        then:
        with(result) {
            overallStats.kdRatio == response.overallStats.kdRatio
            overallStats.winPercentage == response.overallStats.winPercentage
            overallStats.matches == response.overallStats.matches
            overallStats.performance.bestSoloPerformance.map == response.overallStats.performance.bestSoloPerformance.map
            overallStats.performance.bestTeamPerformance.map == response.overallStats.performance.bestTeamPerformance.map
            mapStats.each {
                assert it.csgoMap != null
                assert it.averageKills != null
                assert it.hsPercentage != null
                assert it.matches != null
                assert it.wins != null
                assert it.tripleKills != null
                assert it.quadroKills != null
                assert it.pentaKills != null
            }
        }
    }

    def "should return player history"() {
        given:
        def playerId = PLAYER_ID
        def response = SamplePlayerHistoryResponse.simple()

        playerHistoryClient.getPlayerHistory(playerId) >> Mono.just(response)

        when:
        def result = playerService.getPlayerHistory(playerId).block()

        then:
        result.matchHistory.size() == response.matchHistory.size()
        for (match in result.matchHistory) {
            def comparedTo = response.matchHistory.find({ it.matchId == match.matchId })
            match.eloDiff == comparedTo.eloDiff
            match.elo == comparedTo.elo
            match.date == comparedTo.date
        }
    }
}
