package integration.common.stubs

import groovy.transform.CompileStatic
import integration.common.response.PlayerPositionResponse

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@CompileStatic
class PlayerPositionStubs {

    static def stubSuccessfulApiResponse(String playerId, int position, String region) {
        stubFor(get(urlMatching("/rankings/games/csgo/regions/$region/players/$playerId?(.*?)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(PlayerPositionResponse.simplePosition(position))))
    }

    static def exceptionalThenSuccessful(String playerId, int position, String region) {
        def SCENARIO_NAME = "faceit-retry"
        stubFor(get(urlMatching("/rankings/games/csgo/regions/$region/players/$playerId?(.*?)"))
                .inScenario(SCENARIO_NAME)
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                        .withStatus(INTERNAL_SERVER_ERROR.value()))
                .willSetStateTo("service-unavailable"))

        stubFor(get(urlMatching("/rankings/games/csgo/regions/$region/players/$playerId?(.*?)"))
                .inScenario(SCENARIO_NAME)
                .whenScenarioStateIs("service-unavailable")
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(PlayerPositionResponse.simplePosition(position))))
    }
}
