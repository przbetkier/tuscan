package integration.common.stubs


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class MatchDetailedStubs {

    static void stubSuccessfulResponse(String matchId) {

        stubFor(get(urlMatching("/matches/$matchId/stats"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .withBodyFile("matchDetailsResponse.json")
                ))
    }

    static void stubNotFoundResponse(String matchId) {
        stubFor(get(urlMatching("/matches/$matchId/stats"))
                .willReturn(
                        aResponse()
                                .withStatus(404)
                                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)))
    }

    static void stubFailedResponse(String matchId) {
        stubFor(get(urlMatching("/matches/$matchId/stats"))
                .willReturn(
                        aResponse()
                                .withStatus(503)
                                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)))
    }
}
