package pro.tuscan.client.search

import spock.lang.Specification
import spock.lang.Unroll

class PlayerSearchMapperSpec extends Specification {

    @Unroll
    def "should map faceit response to tuscan search response with #expectedResults players"() {
        given:
        def faceitSearchDTO = new FaceitSearchDTO(new Payload(
                new Players(players)
        ))

        when:
        def result = PlayerSearchMapper.map(faceitSearchDTO)

        then:
        result.players.size() == expectedResults

        where:
        players                                              || expectedResults
        [simplePlayer("1", true), simplePlayer("2", true)]   || 2
        [simplePlayer("1", true), simplePlayer("2", false)]  || 1
        [simplePlayer("1", false), simplePlayer("2", false)] || 0
    }

    private static def simplePlayer(String nickname, boolean withCsgoGame) {
        new PlayerSearch(nickname, "PL", "avatar", true,
                withCsgoGame ? [new SimpleGame("csgo", 5)] : [])
    }
}
