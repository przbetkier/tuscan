package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerCsgoStatsStubs
import integration.common.stubs.PlayerDetailsStubs
import pro.tuscan.common.SamplePlayerDetails
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import java.time.ZonedDateTime

import static integration.common.MockedPlayer.NICKNAME
import static integration.common.MockedPlayer.PLAYER_ID

class PlayerDetailsEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return player details"() {
        given:
        def playerId = PLAYER_ID
        def player = SamplePlayerDetails.simple(playerId)
        def nickname = player.nickname
        PlayerDetailsStubs.stubSuccessfulResponse(player)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details?nickname=$nickname"), Map)

        then:
        response.statusCodeValue == 200
        response.body.nickname == player.nickname
        response.body.playerId == player.playerId
        response.body.gameDetails.steamId == player.games.csgo.steamId
        response.body.membership == "FREE"
    }

    def "should return not found status when player details could not be found"() {
        given:
        def nickname = NICKNAME
        PlayerDetailsStubs.stubNotFoundResponse(nickname)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details?nickname=$nickname"), Map)

        then:
        response.statusCodeValue == 404
    }

    def "should return player csgo details"() {
        given:
        def playerId = PLAYER_ID
        PlayerCsgoStatsStubs.stubSuccessfulResponse(playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details/csgo/$playerId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.overallStats != null
        response.body.mapStats != null
    }

    def "should return 404 status code when player csgo stats could not be found"() {
        given:
        def playerId = PLAYER_ID
        PlayerCsgoStatsStubs.stubNotFound(playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details/csgo/$playerId"), Map)

        then:
        response.statusCodeValue == 404
    }
}
