package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.client.match.MatchFullDetailsDto
import pro.tuscan.client.match.MatchStatsDto
import pro.tuscan.client.match.PlayerDto
import pro.tuscan.client.match.PlayerStatsDto
import pro.tuscan.client.match.RoundStatsDto
import pro.tuscan.client.match.TeamDto
import pro.tuscan.client.match.TeamStatsDto

@CompileStatic
class SampleMatchStatsDto {

    static def zeroKdFirstPlayer(String playerId, String kills, String kdRatio) {
        return create(playerId, kills, kdRatio)
    }

    static def forPlayerWhoLost(String playerId) {
        return create("player-who-won", '14', '4.0')
    }

    static def simple(String playerId) {
        return create(playerId, '14', '4.0')
    }

    private static MatchStatsDto create(String playerId, String kills, String kdRatio) {
        return new MatchStatsDto(
                [
                        new MatchFullDetailsDto(
                                "matchId-1",
                                new RoundStatsDto(
                                        "de_train",
                                        "14",
                                        "16 / 14",
                                        "team-one"
                                ),
                                [
                                        new TeamDto(
                                                "team-one",
                                                new TeamStatsDto(
                                                        "TEAM-ONE"
                                                ),
                                                [new PlayerDto(
                                                        "$playerId",
                                                        "player",
                                                        new PlayerStatsDto(
                                                                "$kills",
                                                                "12",
                                                                "10",
                                                                "5",
                                                                "44",
                                                                "$kdRatio",
                                                                "0.92",
                                                                "3",
                                                                "0",
                                                                "0",
                                                                "1"
                                                        )
                                                )]

                                        ),
                                        new TeamDto(
                                                "team-two",
                                                new TeamStatsDto(
                                                        "TEAM-TWO"
                                                ),
                                                [new PlayerDto(
                                                        "playerId",
                                                        "player",
                                                        new PlayerStatsDto(
                                                                "15",
                                                                "3",
                                                                "11",
                                                                "2",
                                                                "33",
                                                                "1.232",
                                                                "0.92",
                                                                "3",
                                                                "0",
                                                                "0",
                                                                "1"
                                                        )
                                                )]
                                        )
                                ]
                        )
                ]
        )
    }
}
