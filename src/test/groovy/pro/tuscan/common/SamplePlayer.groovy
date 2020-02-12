package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.adapter.api.response.dto.Player
import pro.tuscan.adapter.api.response.dto.PlayerStats

import static integration.common.MockedPlayer.NICKNAME
import static integration.common.MockedPlayer.PLAYER_ID

@CompileStatic
class SamplePlayer {

    static Player simple(String playerId = PLAYER_ID, String nickname = NICKNAME) {
        return new Player(
                playerId,
                nickname,
                new PlayerStats(20, 3, 10, 5, 25, 2.0,
                        1.12, 4, 0, 1, 0
                )
        )
    }
}
