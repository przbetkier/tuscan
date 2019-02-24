package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerHistoryStubs

class PlayerHistoryEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return player history"() {
        given:
        String playerId = "playerId-1"
        PlayerHistoryStubs.successful(playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/player-history/$playerId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.matchHistory != null
        response.body.matchHistory.size > 0
    }
}
