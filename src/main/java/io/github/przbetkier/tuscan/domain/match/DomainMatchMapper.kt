package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.adapter.api.response.dto.Player
import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerStats
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team
import io.github.przbetkier.tuscan.domain.match.DemoStatus.NO_ACTION

class DomainMatchMapper {

    companion object {
        fun fromResponse(response: MatchFullDetailsResponse): Match =
                response.let {
                    Match(
                            matchId = it.matchId,
                            map = it.map,
                            score = it.score,
                            roundsCount = it.roundsCount,
                            teams = mapTeams(it.teams),
                            winnerTeam = it.winnerTeam,
                            demoUrl = it.demoUrl,
                            demoStatus = NO_ACTION
                    )
                }

        private fun mapTeams(teams: List<Team>): List<MatchTeam> =
                teams.map { team ->
                    MatchTeam(
                            team.teamId,
                            team.teamName,
                            team.players.map {
                                MatchPlayer(
                                        it.playerId,
                                        it.nickname,
                                        MatchPlayerStats(
                                                it.playerStats.kills,
                                                it.playerStats.assists,
                                                it.playerStats.deaths,
                                                it.playerStats.headshots,
                                                it.playerStats.headshotPercentage,
                                                it.playerStats.kdRatio,
                                                it.playerStats.krRatio,
                                                it.playerStats.mvps,
                                                it.playerStats.tripleKills,
                                                it.playerStats.quadroKills,
                                                it.playerStats.pentaKills
                                        )
                                )
                            }.toSet()
                    )
                }

        fun toResponse(match: Match, playerId: String): MatchFullDetailsResponse =
                match.let {
                    MatchFullDetailsResponse(
                            it.matchId,
                            it.map,
                            it.score,
                            it.roundsCount,
                            mapToResponseTeams(it.teams),
                            it.winnerTeam,
                            getResult(playerId, it.teams, it.winnerTeam),
                            it.demoUrl,
                            it.demoStatus.name
                    )
                }

        fun updateWithDemoStatus(match: Match, demoStatus: DemoStatus): Match = match.copy(
                demoStatus = demoStatus
        )

        private fun mapToResponseTeams(teams: List<MatchTeam>): List<Team> =
                teams.map { team ->
                    Team(
                            team.teamId,
                            team.teamName,
                            team.players.map {
                                Player(
                                        it.playerId,
                                        it.nickname,
                                        PlayerStats(
                                                it.playerStats.kills,
                                                it.playerStats.assists,
                                                it.playerStats.deaths,
                                                it.playerStats.headshots,
                                                it.playerStats.headshotPercentage,
                                                it.playerStats.kdRatio,
                                                it.playerStats.krRatio,
                                                it.playerStats.mvps,
                                                it.playerStats.tripleKills,
                                                it.playerStats.quadroKills,
                                                it.playerStats.pentaKills
                                        )
                                )
                            }.toSet()
                    )
                }

        private fun getResult(playerId: String, teams: List<MatchTeam>, winnerTeam: String): MatchResult {
            return if (teams.first { t -> t.teamId == winnerTeam }.players.any { p -> p.playerId == playerId })
                MatchResult.WIN else MatchResult.LOSS
        }
    }
}
