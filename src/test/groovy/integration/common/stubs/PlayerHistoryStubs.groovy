package integration.common.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class PlayerHistoryStubs {

    static void successful(String playerId) {
        stubFor(get(urlPathEqualTo("/stats/api/v1/stats/time/users/$playerId/games/csgo"))
                .willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("playerHistoryResponse.json"))
        )
    }
}
