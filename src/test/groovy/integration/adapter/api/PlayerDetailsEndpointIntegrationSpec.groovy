package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerCsgoStatsStubs
import integration.common.stubs.PlayerDetailsStubs
import io.github.przbetkier.tuscan.adapter.api.response.DetailedPlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.adapter.api.response.PlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.common.SamplePlayerDetails
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import java.time.ZonedDateTime

class PlayerDetailsEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return player details"() {
        given:
        def playerId = "playerId-1"
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
        def player = SamplePlayerDetails.simple("playerId-1", "olofmeister")
        def nickname = player.nickname
        PlayerDetailsStubs.stubNotFoundResponse(nickname)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details?nickname=$nickname"), Map)

        then:
        response.statusCodeValue == 404
    }

    def "should return player csgo details"() {
        given:
        def playerId = "playerId-1"
        PlayerCsgoStatsStubs.stubSuccessfulResponse(playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details/csgo/$playerId"), PlayerCsgoStatsResponse)

        then:
        response.statusCodeValue == 200
        response.body.overallStats != null
        response.body.mapStats != null
    }

    def "should return 404 status code when player csgo stats could not be found"() {
        given:
        def playerId = "playerId-1"
        PlayerCsgoStatsStubs.stubNotFound(playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details/csgo/$playerId"), PlayerCsgoStatsResponse)

        then:
        response.statusCodeValue == 404
    }

    @Unroll
    def "should return active ban flag"() {
        given:
        def playerId = "playerId"
        def player = SamplePlayerDetails.banned(playerId, banStartsAt)
        PlayerDetailsStubs.stubSuccessfulResponse(player)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details?nickname=${player.nickname}"), Map)
        def conditions = new PollingConditions()

        then:
        conditions.eventually {
            response.body.ban.active == activeBan
        }

        where:
        banStartsAt                       || activeBan
        ZonedDateTime.now().minusHours(1) || true
        ZonedDateTime.now().plusHours(1)  || false
    }

    def "should get CSGO details for multiple players"() {
        given:
        def player1 = SamplePlayerDetails.simple("playerId-1", "player1")
        def player2 = SamplePlayerDetails.simple("playerId-2", "player2")
        PlayerDetailsStubs.stubSuccessfulResponse(player1)
        PlayerDetailsStubs.stubSuccessfulResponse(player2)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player1.playerId)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player2.playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details/csgo?nickname=${player1.nickname},${player2.nickname}"), DetailedPlayerCsgoStatsResponse[].class)

        then:
        response.statusCodeValue == 200
        response.body.size() == 2
        response.body.find({it.nickname == player1.nickname}) != null
        response.body.find({it.nickname == player2.nickname}) != null
        response.body.each {it.mapStats != null && it.overallStats != null}
    }

    def "should get CSGO details only for available players"() {
        given:
        def player1 = SamplePlayerDetails.simple("playerId-1", "player1")
        def player2 = SamplePlayerDetails.simple("playerId-2", "player2")
        PlayerDetailsStubs.stubSuccessfulResponse(player1)
        PlayerDetailsStubs.stubNotFoundResponse(player2.nickname)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player1.playerId)
        PlayerCsgoStatsStubs.stubNotFound(player2.playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details/csgo?nickname=${player1.nickname},${player2.nickname}"), DetailedPlayerCsgoStatsResponse[].class)

        then:
        response.statusCodeValue == 200
        response.body.size() == 1
        response.body.find({it.nickname == player1.nickname}) != null
        response.body.find({it.nickname == player2.nickname}) == null
    }
}
