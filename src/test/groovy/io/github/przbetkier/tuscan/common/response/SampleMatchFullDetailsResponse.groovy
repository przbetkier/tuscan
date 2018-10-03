package io.github.przbetkier.tuscan.common.response

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team
import io.github.przbetkier.tuscan.adapter.api.response.dto.TeamStats
import io.github.przbetkier.tuscan.common.SamplePlayer

class SampleMatchFullDetailsResponse {

    static def simple() {
        return new MatchFullDetailsResponse(
                "matchId-1",
                "de_inferno",
                "16/7",
                23,
                [
                        new Team(
                                "team-1",
                                new TeamStats(
                                        "team-One",
                                        3.33),
                                [SamplePlayer.simple(), SamplePlayer.simple()].toSet()),
                        new Team(
                                "team-1",
                                new TeamStats(
                                        "team-One",
                                        3.33),
                                [SamplePlayer.simple(), SamplePlayer.simple()].toSet()),
                ],
                "team-1",
                MatchResult.WIN
        )
    }
}
