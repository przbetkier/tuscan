package integration.adapter.api

import integration.BaseIntegrationSpec

import java.time.LocalDateTime

import static integration.common.stubs.MatchSimpleDetailsStub.stubSuccessfulResponse

class MatchesEndpointIntegrationSpec extends BaseIntegrationSpec {

    def "should return match list with ids and dates"() {
        given:
        def playerId = UUID.randomUUID().toString()
        def matchId = UUID.randomUUID().toString()
        def startedAt = LocalDateTime.of(2018, 10, 1, 10, 0)
        def finishedAt = LocalDateTime.of(2018, 10, 1, 11, 0)
        stubSuccessfulResponse(startedAt, finishedAt, matchId, playerId)

        when:
        def response = restTemplate.getForEntity(localUrl("/faceit/matches/simple?playerId=$playerId&offset=0"), Map)

        then:
        response.statusCodeValue == 200
        response.body.simpleMatchList.get(0).matchId == matchId
        response.body.simpleMatchList.get(0).startedAt == '2018-10-01T10:00:00'
        response.body.simpleMatchList.get(0).finishedAt == '2018-10-01T11:00:00'
        response.body.matchesCount == 1
    }
}
