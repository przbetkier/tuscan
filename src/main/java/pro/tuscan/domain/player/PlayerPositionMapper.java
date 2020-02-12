package pro.tuscan.domain.player;

import pro.tuscan.adapter.api.response.PlayerPositionResponse;
import pro.tuscan.client.player.Position;
import reactor.util.function.Tuple2;

public class PlayerPositionMapper {

    private PlayerPositionMapper() {
    }

    public static PlayerPositionResponse map(String playerId, Tuple2<Position, Position> tuple) {
        return new PlayerPositionResponse(playerId,
                                          Integer.valueOf(tuple.getT1().getPosition()),
                                          Integer.valueOf(tuple.getT2().getPosition()));
    }
}
