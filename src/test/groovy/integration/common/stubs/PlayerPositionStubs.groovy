package integration.common.stubs

import integration.common.response.PlayerPositionResponse

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class PlayerPositionStubs {

    static void stubSuccessfulApiResponse(String playerId, int position, String region) {

        stubFor(get(urlMatching("/rankings/games/csgo/regions/$region/players/$playerId?(.*?)"))
                .willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(PlayerPositionResponse.simplePosition(position))
        ))
    }
}
