package integration.adapter.api

import com.github.tomakehurst.wiremock.client.WireMock
import integration.BaseIntegrationSpec
import integration.common.stubs.MatchDemoStubs
import integration.common.stubs.MatchDetailsStubs
import integration.common.stubs.MatchSimpleStubs
import integration.common.stubs.PlayerHistoryStubs

import java.time.LocalDateTime
import java.time.ZoneOffset

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static integration.common.MockedPlayer.PLAYER_ID

class MatchesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return match list with ids and dates"() {
        given:
        def playerId = PLAYER_ID
        def matchId = UUID.randomUUID().toString()
        def startedAt = LocalDateTime.of(2018, 10, 1, 10, 0).toInstant(ZoneOffset.UTC)
        def finishedAt = LocalDateTime.of(2018, 10, 1, 11, 0).toInstant(ZoneOffset.UTC)
        MatchSimpleStubs.stubSuccessfulResponse(startedAt, finishedAt, matchId, playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches/simple?playerId=$playerId&offset=0"), Map)

        then:
        response.statusCodeValue == 200
        response.body.simpleMatchList[0].matchId == matchId
        response.body.simpleMatchList[0].startedAt == '2018-10-01T10:00:00Z'
        response.body.simpleMatchList[0].finishedAt == '2018-10-01T11:00:00Z'
        response.body.matchesCount == 1
    }

    def "should return match list with ids and dates even if main api is down"() {
        given:
        def playerId = PLAYER_ID
        MatchSimpleStubs.stubFailedResponse(playerId)
        PlayerHistoryStubs.successful(playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches/simple?playerId=$playerId&offset=0"), Map)

        then:
        response.statusCodeValue == 200
        WireMock.verify(1, getRequestedFor(urlMatching("/stats/api/v1/stats/time/users/$playerId/games/csgo(.*?)")));
    }

    def "should return match details on successful Faceit response"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailsStubs.stubSuccessfulResponse(matchId)
        MatchDemoStubs.stubSuccessfulResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.result == 'WIN'
        response.body.demoUrl == 'https://demos-europe-west2.faceit-cdn.net/csgo/2cf3c2b7-255e-458c-9a41-0ea1d5bdeede.dem.gz'
        response.body.demoStatus == 'NO_ACTION'
    }

    def "should return 404 when match could not be found on Faceit"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailsStubs.stubNotFoundResponse(matchId)
        MatchDemoStubs.stubSuccessfulResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 404
    }

    def "should return 500 when Faceit is down"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailsStubs.stubFailedResponse(matchId)
        MatchDemoStubs.stubSuccessfulResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 500
    }

    def "should return match details on successful Faceit response even if demo was not found"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailsStubs.stubSuccessfulResponse(matchId)
        MatchDemoStubs.stubNotFoundResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.result == 'WIN'
        response.body.demoUrl == null
    }

    def "should return match details on successful Faceit response even if demo fetching threw an error"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailsStubs.stubSuccessfulResponse(matchId)
        MatchDemoStubs.stubFailedResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.result == 'WIN'
        response.body.demoUrl == null
    }

    def "should return match details on successful Faceit response even if demo fetching returned no urls"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailsStubs.stubSuccessfulResponse(matchId)
        MatchDemoStubs.stubFailedResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.result == 'WIN'
        response.body.demoUrl == null
    }
}
