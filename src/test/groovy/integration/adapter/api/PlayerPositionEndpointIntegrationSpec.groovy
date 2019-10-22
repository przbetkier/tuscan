package integration.adapter.api

import com.github.tomakehurst.wiremock.client.WireMock
import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerPositionStubs
import spock.util.concurrent.PollingConditions

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.moreThan
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static integration.common.MockedPlayer.PLAYER_ID

class PlayerPositionEndpointIntegrationSpec extends BaseIntegrationSpec {

    PollingConditions pollingConditions = new PollingConditions()

    def "should return player position with ok status"() {
        given:
        def playerId = PLAYER_ID
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

    def "should retry when service unavailable and get player position eventually"() {
        given:
        def playerId = PLAYER_ID
        def region = "EU"
        def country = "pl"
        def position = 101
        PlayerPositionStubs.exceptionalThenSuccessful(playerId, position, region)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/player/position?playerId=$playerId&region=$region&country=$country"), Map)

        then:
        response.statusCodeValue == 200
        pollingConditions.within(5, {
            WireMock.verify(moreThan(2), getRequestedFor(urlMatching("/rankings/games/csgo/regions/$region/players/$playerId?(.*?)")))
        })
    }
}
