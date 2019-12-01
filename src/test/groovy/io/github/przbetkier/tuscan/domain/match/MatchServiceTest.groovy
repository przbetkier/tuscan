package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.client.match.FaceitMatchClient
import io.github.przbetkier.tuscan.common.SampleMatch
import io.github.przbetkier.tuscan.common.response.SampleMatchFullDetailsResponse
import io.github.przbetkier.tuscan.common.response.SampleMatchResponse
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject

import static integration.common.MockedPlayer.PLAYER_ID
import static io.github.przbetkier.tuscan.common.SampleMatch.MATCH_ID

class MatchServiceTest extends Specification {

    FaceitMatchClient faceitMatchClient = Mock(FaceitMatchClient)
    MatchRepository matchRepository = Mock(MatchRepository)

    @Subject
    MatchService matchService = new MatchService(faceitMatchClient, matchRepository)

    def "should return a response with simple match list"() {
        given:
        def playerId = PLAYER_ID
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
        def playerId = PLAYER_ID
        def offset = 0
        def response = SampleMatchResponse.simple()
        faceitMatchClient.getMatches(playerId, offset) >> Mono.error(new RuntimeException())

        when:
        def matchesResponse = matchService.getMatches(playerId, offset).block()

        then:
        1 * faceitMatchClient.fallbackToV1Matches(playerId) >> Mono.just(response)
        matchesResponse.simpleMatchList.each { m -> m.startedAt && m.finishedAt && m.matchId }
        matchesResponse.matchesCount == response.matchesCount
    }

    def "should not call Faceit API when match is saved in database"() {
        given:
        def matchId = MATCH_ID
        def match = SampleMatch.simple(matchId)
        def playerInMatchId = match.teams.first().players.first().playerId

        when:
        def matchResponse = matchService.getMatchByPlayer(matchId, playerInMatchId).block()

        then:
        1 * matchRepository.findById(matchId) >> Mono.just(match)
        0 * faceitMatchClient.getMatchDetails(matchId, playerInMatchId)
        matchResponse.matchId == match.matchId
    }

    def "should call Faceit API when match is not saved in database and save it"() {
        given:
        def match = SampleMatchFullDetailsResponse.simple()
        def matchId = match.matchId
        def playerId = match.teams.first().players.first().playerId

        when:
        def matchResponse = matchService.getMatchByPlayer(matchId, playerId).block()

        then:
        1 * matchRepository.findById(matchId) >> Mono.empty()
        1 * faceitMatchClient.getMatchDetails(matchId, playerId) >> Mono.just(match)
        1 * matchRepository.save(_ as Match) >> Mono.just(DomainMatchMapper.@Companion.fromResponse(match))
        matchResponse.matchId == match.matchId
    }
}
