package integration.common.stubs

import groovy.transform.CompileStatic
import integration.common.response.MatchSimpleDetailsResponse

import java.time.LocalDateTime

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@CompileStatic
class MatchSimpleStubs {

    static void stubSuccessfulResponse(LocalDateTime startedAt, LocalDateTime finishedAt, String matchId, String playerId) {

        stubFor(get(urlMatching("/players/$playerId/history(.*?)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(MatchSimpleDetailsResponse.successfulResponse(startedAt, finishedAt, matchId))
                ))
    }

    static void stubFailedResponse(String playerId) {

        stubFor(get(urlMatching("/players/$playerId/history(.*?)"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)))
    }
}
