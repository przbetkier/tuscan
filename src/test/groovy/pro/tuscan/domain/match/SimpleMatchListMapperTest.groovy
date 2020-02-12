package pro.tuscan.domain.match

import pro.tuscan.client.match.MatchesSimpleDetailsDto
import pro.tuscan.client.match.OpenMatchSimpleDetailsDto
import pro.tuscan.client.match.SimpleMatchDto
import spock.lang.Specification

class SimpleMatchListMapperTest extends Specification {

    def "should map dto to response"() {
        given:
        def startedAt = 1557057600 // 2019 may 05 12:00 UTC in s
        def finishedAt = 1557061200 // // 2019 may 05 13:00 UTC in s
        MatchesSimpleDetailsDto dto = new MatchesSimpleDetailsDto(
                [
                        new SimpleMatchDto("matchId-1", startedAt, finishedAt),
                        new SimpleMatchDto("matchId-2", startedAt, finishedAt),
                        new SimpleMatchDto("matchId-3", startedAt, finishedAt)
                ]
        )

        when:
        def result = SimpleMatchListMapper.map(dto)

        then:
        result.matchesCount == dto.matchesCount
        result.simpleMatchList.size() == dto.matchesCount
        result.simpleMatchList.get(0).matchId == dto.simpleMatchList.get(0).matchId
        result.simpleMatchList.get(0).startedAt.toString() == '2019-05-05T12:00:00Z'
        result.simpleMatchList.get(0).finishedAt.toString() == '2019-05-05T13:00:00Z'
    }

    def "should map dto to response for open faceit api"() {
        given:
        def startedAt = 1557057600999 // 2019 may 05 12:00 UTC in ms
        def finishedAt = 1557061200999 // // 2019 may 05 13:00 UTC in ms
        List<OpenMatchSimpleDetailsDto> matches =
                [
                        new OpenMatchSimpleDetailsDto("matchId-1", startedAt, finishedAt),
                        new OpenMatchSimpleDetailsDto("matchId-2", startedAt, finishedAt),
                        new OpenMatchSimpleDetailsDto("matchId-3", startedAt, finishedAt)
                ]

        when:
        def result = SimpleMatchListMapper.mapForOpenApi(matches)

        then:
        result.matchesCount == matches.size()
        result.simpleMatchList.size() == matches.size()
        result.simpleMatchList.get(0).matchId == matches.get(0).id
        result.simpleMatchList.get(0).startedAt.toString() == '2019-05-05T12:00:00Z'
        result.simpleMatchList.get(0).finishedAt.toString() == '2019-05-05T13:00:00Z'
    }
}
