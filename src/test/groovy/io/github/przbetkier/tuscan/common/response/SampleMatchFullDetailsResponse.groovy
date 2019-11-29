package io.github.przbetkier.tuscan.common.response

import groovy.transform.CompileStatic
import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.adapter.api.response.dto.Player
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team

import io.github.przbetkier.tuscan.common.SamplePlayer
import io.github.przbetkier.tuscan.domain.match.DemoStatus

@CompileStatic
class SampleMatchFullDetailsResponse {

    static def simple() {
        Set<Player> teamOnePlayers = [SamplePlayer.simple("playerId-2"), SamplePlayer.simple("playerId-2")].toSet()
        Set<Player> teamTwoPlayers = [SamplePlayer.simple("playerId-3"), SamplePlayer.simple("playerId-4")].toSet()

        return new MatchFullDetailsResponse(
                "matchId-1",
                "de_inferno",
                "16/7",
                23,
                [
                        new Team(
                                "teamId-1",
                                "Team-One",
                                teamOnePlayers),
                        new Team(
                                "teamId-2",
                                "Team-Two",
                                teamTwoPlayers,
                        )
                ],
                "team-1",
                MatchResult.WIN,
                "url-1",
                DemoStatus.NO_ACTION.name()
        )
    }
}
