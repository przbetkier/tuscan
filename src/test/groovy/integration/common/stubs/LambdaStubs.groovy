package integration.common.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class LambdaStubs {

    static void stubSuccessfulResponse() {
        stubFor(post(urlMatching("/default/demo-parser"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("lambdaSuccessfulResponse.json")
                ))
    }

    static void stubExceptionalResponse() {
        stubFor(post(urlMatching("/default/demo-parser"))
                .willReturn(aResponse()
                        .withStatus(500)
                ))
    }
}
