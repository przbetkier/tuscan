package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.stubs.PlayerBanStubs
import pro.tuscan.client.player.BanInfoResponse

class PlayerBansEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return player bans"() {
        given:
        def playerId = 'playerId-1'
        def reason = 'cheating'
        PlayerBanStubs.stubSuccessfulResponse(playerId, reason, null)

        when:
        def response = restTemplate.getForEntity("/faceit/player/$playerId/bans", BanInfoResponse.class)

        then:
        response.statusCode.is2xxSuccessful()
        with(response.body.bans) {
            size() == 1
            first().reason == reason
            first().endsAt == null // permanent
        }
    }
}
