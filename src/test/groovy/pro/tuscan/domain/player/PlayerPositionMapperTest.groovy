package pro.tuscan.domain.player

import pro.tuscan.client.player.Position
import reactor.util.function.Tuple2
import spock.lang.Specification

class PlayerPositionMapperTest extends Specification {

    def "should map to player position response"() {
        given:
        def playerId = "playerId"
        def regionPosition = new Position("32119")
        def countryPosition = new Position("3999")

        when:
        def response = PlayerPositionMapper.@Companion.map(playerId, new Tuple2<Position, Position>(regionPosition, countryPosition))

        then:
        response.playerId == playerId
        response.positionInRegion == 32119
        response.positionInCountry == 3999
    }
}
