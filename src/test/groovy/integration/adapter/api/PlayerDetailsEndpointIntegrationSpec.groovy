package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerPositionStubs

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
}
