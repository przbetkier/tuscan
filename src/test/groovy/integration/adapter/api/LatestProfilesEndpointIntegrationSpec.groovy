package integration.adapter.api

import integration.BaseIntegrationSpec
import integration.common.SampleLatestProfile
import integration.common.request.LatestProfileSampleRequest
import integration.common.stubs.PlayerCsgoStatsStubs
import integration.common.stubs.PlayerDetailsStubs
import io.github.przbetkier.tuscan.common.SamplePlayerDetails
import io.github.przbetkier.tuscan.domain.profiles.LatestProfile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

import java.time.Instant

import static integration.common.MockedPlayer.NICKNAME
import static integration.common.MockedPlayer.PLAYER_ID
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE

class LatestProfilesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return four latest profiles ordered by date"() {
        given:
        def latestProfiles = generateLatestProfiles(6)
        latestProfileRepository.saveAll(latestProfiles).blockLast()

        when:
        def response = restTemplate.getForEntity(localUrl("/tuscan-api/latest-profiles"), List)

        then:
        response.statusCodeValue == 200
        response.body.size() == 6
    }

    def "should save new profile"() {
        given:
        def request = LatestProfileSampleRequest.simple()
        def entity = new HttpEntity(request, null)

        assert latestProfileRepository.count().block() == 0L

        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/latest-profiles"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == 201
        with(response.body) {
            nickname == request.nickname
            level == request.level
            kdRatio == request.kdRatio
            avatarUrl == request.avatarUrl
            elo == request.elo
        }
        latestProfileRepository.count().block() == 1L
    }

    def "should update existing profile"() {
        given:
        def nickname = "nickname"
        def latestProfile = new LatestProfile(nickname, "http://avatar.url", 10, 2200, 1.31, Instant.now())
        latestProfileRepository.save(latestProfile).block()

        def request = LatestProfileSampleRequest.simple(nickname)
        def entity = new HttpEntity(request, null)

        assert latestProfileRepository.count().block() == 1L

        when:
        def response = restTemplate.exchange(localUrl("/tuscan-api/latest-profiles"), HttpMethod.POST, entity, Map)

        then:
        response.statusCodeValue == 201
        with(response.body) {
            nickname == request.nickname
            level == request.level
            kdRatio == request.kdRatio
            avatarUrl == request.avatarUrl
            elo == request.elo
        }
        latestProfileRepository.count().block() == 1L
    }

    def "should receive 2 SSE with new latest searched profiles"() {
        given:
        def player1 = SamplePlayerDetails.simple(PLAYER_ID, NICKNAME)
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
            latestProfiles.add(SampleLatestProfile.simple("nickname-$it"))
        }
        return latestProfiles
    }
}
