package integration.adapter.api

import com.github.tomakehurst.wiremock.client.WireMock
import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerPositionStubs

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching

class PlayerDetailsEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return player position with ok status"() {
        given:
        def playerId = "playerId"
        def region = "EU"
        def country = "pl"
        def position = 101
        PlayerPositionStubs.stubSuccessfulApiResponse(playerId, position, region)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/player/position?playerId=$playerId&region=$region&country=$country"), Map)

        then:
        response.statusCodeValue == 200
        response.body.playerId == playerId
        response.body.positionInRegion == position
        response.body.positionInCountry == position
    }

    def "should retry when service unavailable and get player position"() {
        given:
        def playerId = "playerId"
        def region = "EU"
        def country = "pl"
        def position = 101
        PlayerPositionStubs.exceptionalThenSuccessful(playerId, position, region)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/player/position?playerId=$playerId&region=$region&country=$country"), Map)

        then:
        response.statusCodeValue == 200
        def expectedRequest = 4 // 2 * 1 fail + 2 * 1 success
        WireMock.verify(expectedRequest, getRequestedFor(urlMatching("/rankings/games/csgo/regions/$region/players/$playerId?(.*?)")))
    }
}
