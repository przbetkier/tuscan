package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerCsgoStatsStubs
import integration.common.stubs.PlayerDetailsStubs
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
}
