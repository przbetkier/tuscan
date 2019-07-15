package integration.common.stubs

import integration.common.response.PlayerCsgoStatsResponse

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class PlayerCsgoStatsStubs {

    static def stubSuccessfulResponse(String playerId) {
        stubFor(get(urlEqualTo("/players/$playerId/stats/csgo"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .withBody(PlayerCsgoStatsResponse.successful())))
    }
}
