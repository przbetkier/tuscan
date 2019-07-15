package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.LatestProfileSample
import integration.common.stubs.PlayerCsgoStatsStubs
import integration.common.stubs.PlayerDetailsStubs
import io.github.przbetkier.tuscan.common.SamplePlayerDetails
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE

class LatestProfilesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return four latest profiles ordered by date"() {
        given:
        def latestProfiles = generateLatestProfiles(4)
        latestProfileRepository.saveAll(latestProfiles).blockLast()

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/latest-profiles"), List)

        then:
        response.statusCodeValue == 200
        response.body.size() == 4
    }

    def "should save new profile"() {
        given:
        def player = SamplePlayerDetails.simple()
        def entity = new HttpEntity(player.nickname, null)
        PlayerDetailsStubs.stubSuccessfulResponse(player)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player.playerId)

        when:
        def request = restTemplate.exchange(localUrl("/tuscan-api/latest-profiles"), HttpMethod.POST, entity, Map)

        then:
        request.statusCodeValue == 201
    }

    def "should receive 2 SSE with new latest searched profiles"() {
        given:
        def player1 = SamplePlayerDetails.simple("playerId-1", "nickname-1")
        def entity1 = new HttpEntity(player1.nickname, null)
        PlayerDetailsStubs.stubSuccessfulResponse(player1)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player1.playerId)

        def player2 = SamplePlayerDetails.simple("playerId-2", "nickname-2")
        def entity2 = new HttpEntity(player2.nickname, null)
        PlayerDetailsStubs.stubSuccessfulResponse(player2)
        PlayerCsgoStatsStubs.stubSuccessfulResponse(player2.playerId)

        def client = WebClient.builder()
                .baseUrl("http://localhost:$port")
                .defaultHeader(ACCEPT, TEXT_EVENT_STREAM_VALUE)
                .build()

        def result = client.method(HttpMethod.GET)
                .uri("/tuscan-api/latest-profiles/sse")
                .retrieve()
                .bodyToFlux(String.class)

        when:
        restTemplate.exchange(localUrl("/tuscan-api/latest-profiles"), HttpMethod.POST, entity1, Map)
        restTemplate.exchange(localUrl("/tuscan-api/latest-profiles"), HttpMethod.POST, entity2, Map)

        then:
        StepVerifier.create(result)
                .expectNextCount(2)
                .thenCancel()
                .verify()
    }

    static def generateLatestProfiles(int count) {
        def latestProfiles = []
        count.times {
            latestProfiles.add(LatestProfileSample.simple("nickname-$it"))
        }
        return latestProfiles
    }
}
