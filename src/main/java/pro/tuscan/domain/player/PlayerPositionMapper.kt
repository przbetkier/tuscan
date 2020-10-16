package pro.tuscan.domain.player

import pro.tuscan.adapter.api.response.PlayerPositionResponse
import pro.tuscan.client.player.Position
import reactor.util.function.Tuple2

object PlayerPositionMapper {

    @JvmStatic
    fun map(playerId: String, tuple: Tuple2<Position, Position>): PlayerPositionResponse =
            PlayerPositionResponse(playerId, tuple.t1.position.toInt(), tuple.t2.position.toInt())

}
