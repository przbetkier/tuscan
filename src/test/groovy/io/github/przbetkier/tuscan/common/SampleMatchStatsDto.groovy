package io.github.przbetkier.tuscan.common

import io.github.przbetkier.tuscan.client.match.MatchFullDetailsDto
import io.github.przbetkier.tuscan.client.match.MatchStatsDto
import io.github.przbetkier.tuscan.client.match.PlayerDto
import io.github.przbetkier.tuscan.client.match.PlayerStatsDto
import io.github.przbetkier.tuscan.client.match.RoundStatsDto
import io.github.przbetkier.tuscan.client.match.TeamDto
import io.github.przbetkier.tuscan.client.match.TeamStatsDto


class SampleMatchStatsDto {

    static def simple(String playerId) {
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
                                                        "TEAM-ONE",
                                                        "3.4"
                                                ),
                                                [new PlayerDto(
                                                        "$playerId",
                                                        "player",
                                                        new PlayerStatsDto(
                                                                "20",
                                                                "12",
                                                                "10",
                                                                "5",
                                                                "44",
                                                                "1.232",
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
                                                        "TEAM-TWO",
                                                        "3.1"
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
