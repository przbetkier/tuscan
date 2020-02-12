package pro.tuscan.common

import groovy.transform.CompileStatic
import pro.tuscan.domain.match.DemoStatus
import pro.tuscan.domain.match.Match
import pro.tuscan.domain.match.MatchTeam

@CompileStatic
class SampleMatch {

    public static final String MATCH_ID = "matchId-1"

    static Match simple(String matchId = MATCH_ID, DemoStatus status = DemoStatus.NO_ACTION) {

        def teamOnePlayers = [SampleMatchPlayer.simple("playerId-1"),
                              SampleMatchPlayer.simple("playerId-2")].toSet()
        def teamTwoPlayers = [SampleMatchPlayer.simple("playerId-3"),
                              SampleMatchPlayer.simple("playerId-4")].toSet()

        return new Match(
                matchId,
                "de_inferno",
                "16/7",
                23,
                [
                        new MatchTeam(
                                "teamId-1",
                                "Team-One",
                                teamOnePlayers),
                        new MatchTeam(
                                "teamId-2",
                                "Team-Two",
                                teamTwoPlayers,
                        )
                ],
                "teamId-1",
                "demoUrl-1",
                status
        )
    }
}
