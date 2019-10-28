package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerCsgoStatsStubs
import integration.common.stubs.PlayerDetailsStubs
import io.github.przbetkier.tuscan.adapter.api.response.DetailedPlayerCsgoStatsResponse
import io.github.przbetkier.tuscan.common.SamplePlayerDetails

class PlayerDetailsPluginEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should get CSGO details for multiple players"() {
        given:
        def player1 = SamplePlayerDetails.simple("playerId-1", "player1")
        def player2 = SamplePlayerDetails.simple("playerId-2", "player2")
        PlayerDetailsStubs.stubSuccessfulResponse(player1)
        PlayerDetailsStubs.stubSuccessfulResponse(player2)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player1.playerId)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player2.playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/plugin/players/details/csgo?nickname=${player1.nickname},${player2.nickname}"), Map[].class)

        then:
        response.statusCodeValue == 200
        response.body.size() == 2
        response.body.find({it.nickname == player1.nickname}) != null
        response.body.find({it.nickname == player2.nickname}) != null
        response.body.each {it.mapStats != null && it.overallStats != null}
    }

    def "should get CSGO details only for players that still exist"() {
        given:
        def player1 = SamplePlayerDetails.simple("playerId-1", "player1")
        def player2 = SamplePlayerDetails.simple("playerId-2", "player2")
        PlayerDetailsStubs.stubSuccessfulResponse(player1)
        PlayerDetailsStubs.stubNotFoundResponse(player2.nickname)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player1.playerId)
        PlayerCsgoStatsStubs.stubNotFound(player2.playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/plugin/players/details/csgo?nickname=${player1.nickname},${player2.nickname}"), Map[].class)

        then:
        response.statusCodeValue == 200
        response.body.size() == 1
        response.body.find({it.nickname == player1.nickname}) != null
        response.body.find({it.nickname == player2.nickname}) == null
    }

    def "should get CSGO details only for players players with csgo stats"() {
        given:
        def player1 = SamplePlayerDetails.simple("playerId-1", "player1")
        def player2 = SamplePlayerDetails.simple("playerId-2", "player2")
        PlayerDetailsStubs.stubSuccessfulResponse(player1)
        PlayerDetailsStubs.stubSuccessfulResponse(player2)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player1.playerId)
        PlayerCsgoStatsStubs.stubNotFound(player2.playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/plugin/players/details/csgo?nickname=${player1.nickname},${player2.nickname}"), Map[].class)

        then:
        response.statusCodeValue == 200
        response.body.size() == 1
        response.body.find({it.nickname == player1.nickname}) != null
        response.body.find({it.nickname == player2.nickname}) == null
    }
}
