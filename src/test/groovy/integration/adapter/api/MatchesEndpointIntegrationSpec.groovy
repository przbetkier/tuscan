package integration.adapter.api

import com.github.tomakehurst.wiremock.client.WireMock
import integration.BaseIntegrationSpec
import integration.common.stubs.MatchDetailedStubs
import integration.common.stubs.PlayerHistoryStubs

import java.time.LocalDateTime

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static integration.common.MockedPlayer.PLAYER_ID
import static integration.common.stubs.MatchSimpleStubs.stubFailedResponse
import static integration.common.stubs.MatchSimpleStubs.stubSuccessfulResponse

class MatchesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return match list with ids and dates"() {
        given:
        def playerId = PLAYER_ID
        def matchId = UUID.randomUUID().toString()
        def startedAt = LocalDateTime.of(2018, 10, 1, 10, 0)
        def finishedAt = LocalDateTime.of(2018, 10, 1, 11, 0)
        stubSuccessfulResponse(startedAt, finishedAt, matchId, playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches/simple?playerId=$playerId&offset=0"), Map)

        then:
        response.statusCodeValue == 200
        response.body.simpleMatchList[0].matchId == matchId
        response.body.simpleMatchList[0].startedAt == '2018-10-01T10:00:00'
        response.body.simpleMatchList[0].finishedAt == '2018-10-01T11:00:00'
        response.body.matchesCount == 1
    }

    def "should return match list with ids and dates even if main api is down"() {
        given:
        def playerId = PLAYER_ID
        stubFailedResponse(playerId)
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

        MatchDetailedStubs.stubSuccessfulResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 200
        response.body.result == 'WIN'
    }

    def "should return 404 when match could not be found on Faceit"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailedStubs.stubNotFoundResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 404
    }

    def "should return 500 when Faceit is down"() {
        given:
        def matchId = "1-0e3a1d1d-f0f4-467a-a6bf-87edcb344b04"
        def playerId = "ac71ba3c-d3d4-45e7-8be2-26aa3986867d"

        MatchDetailedStubs.stubFailedResponse(matchId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches?playerId=$playerId&matchId=$matchId"), Map)

        then:
        response.statusCodeValue == 500
    }
}
