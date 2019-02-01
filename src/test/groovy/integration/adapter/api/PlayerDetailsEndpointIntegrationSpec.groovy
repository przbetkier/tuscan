package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerDetailsStubs
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

    def "should return not found status"() {
        given:
        def player = SamplePlayerDetails.simple("playerId-1", "olofmeister")
        def nickname = player.nickname
        PlayerDetailsStubs.stubNotFoundResponse(nickname)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/players/details?nickname=$nickname"), Map)

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
