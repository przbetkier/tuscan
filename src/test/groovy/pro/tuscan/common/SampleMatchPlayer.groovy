package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.domain.match.MatchPlayer
import pro.tuscan.domain.match.MatchPlayerStats

@CompileStatic
class SampleMatchPlayer {

    static MatchPlayer simple(String playerId = "playerId", String nickname = "nickname") {
        return new MatchPlayer(playerId, nickname,
                new MatchPlayerStats(20, 3, 10, 5, 25, 2.0,
                        1.12, 4, 0, 1, 0
                ))
    }
}
