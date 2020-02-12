package integration.common.stubs

import groovy.transform.CompileStatic
import integration.common.response.PlayerDetailsResponse
import pro.tuscan.client.player.PlayerDetails

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@CompileStatic
class PlayerDetailsStubs {

    static def stubSuccessfulResponse(PlayerDetails playerDetails) {
        def nickname = playerDetails.nickname
        stubFor(get(urlEqualTo("/players?nickname=$nickname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(PlayerDetailsResponse.simple(playerDetails))))
    }

    static def stubNotFoundResponse(String nickname) {
        stubFor(get(urlEqualTo("/players?nickname=$nickname"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("playerDetailsNotFound.json")))
    }
}
