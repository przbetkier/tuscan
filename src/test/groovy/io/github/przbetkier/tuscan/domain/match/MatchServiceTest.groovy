package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.client.match.FaceitMatchClient
import io.github.przbetkier.tuscan.common.response.SampleMatchFullDetailsResponse
import io.github.przbetkier.tuscan.common.response.SampleMatchResponse
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject

class MatchServiceTest extends Specification {

    FaceitMatchClient faceitMatchClient = Mock(FaceitMatchClient)

    @Subject
    MatchService matchService = new MatchService(faceitMatchClient)

    def "should return a response with simple match list"() {
        given:
        def playerId = "playerId-1"
        def offset = 0
        def response = SampleMatchResponse.simple()
        faceitMatchClient.getMatches(playerId, offset) >> Mono.just(response)

        when:
        def matchesResponse = matchService.getMatches(playerId, offset).block()

        then:
        matchesResponse.simpleMatchList.each { m -> m.startedAt && m.finishedAt && m.matchId }
        matchesResponse.matchesCount == response.matchesCount
        // Also should not call fallback method
        0 * faceitMatchClient.fallbackToV1Matches(playerId)
    }

    def "should fallback to v1 method and return simple matches when main method failed"() {
        given:
        def playerId = "playerId-1"
        def offset = 0
        def response = SampleMatchResponse.simple()
        faceitMatchClient.getMatches(playerId, offset) >> Mono.empty()

        when:
        def matchesResponse = matchService.getMatches(playerId, offset).block()

        then:
        1 * faceitMatchClient.fallbackToV1Matches(playerId) >> Mono.just(response)
        matchesResponse.simpleMatchList.each { m -> m.startedAt && m.finishedAt && m.matchId }
        matchesResponse.matchesCount == response.matchesCount
    }

    def "should return a response with match details"() {
        given:
        def playerId = "playerId-1"
        def matchId = "matchId-1"
        def response = SampleMatchFullDetailsResponse.simple()
        faceitMatchClient.getMatchDetails(matchId, playerId) >> response

        when:
        def details = matchService.getMatch(matchId, playerId)

        then:
        details.result == response.result
        details.matchId == response.matchId
    }
}
