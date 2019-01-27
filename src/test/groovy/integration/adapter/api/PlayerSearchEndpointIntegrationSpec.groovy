package integration.adapter.api

import integration.BaseIntegrationSpec
import io.github.przbetkier.tuscan.adapter.api.response.PlayerSearchResponse

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class PlayerSearchEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return a player search response"() {
        given:
        def nickname = 'olof'

        stubFor(get(urlPathMatching("/search/v1"))
                .withQueryParam('limit', equalTo('20'))
                .withQueryParam('query', equalTo(nickname))
                .willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("query.json"))
        )

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/search/players?nickname=$nickname"), Map)

        then:
        response.statusCodeValue == 200
        response.body.players.size() == 3
    }
}
