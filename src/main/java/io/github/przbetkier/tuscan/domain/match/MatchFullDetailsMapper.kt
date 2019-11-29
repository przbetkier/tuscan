package io.github.przbetkier.tuscan.domain.match

import io.github.przbetkier.tuscan.adapter.api.response.MatchFullDetailsResponse
import io.github.przbetkier.tuscan.adapter.api.response.dto.MatchResult
import io.github.przbetkier.tuscan.adapter.api.response.dto.Player
import io.github.przbetkier.tuscan.adapter.api.response.dto.PlayerStats
import io.github.przbetkier.tuscan.adapter.api.response.dto.Team
import io.github.przbetkier.tuscan.client.match.MatchDemoDto
import io.github.przbetkier.tuscan.client.match.MatchStatsDto
import io.github.przbetkier.tuscan.client.match.PlayerDto
import io.github.przbetkier.tuscan.domain.stats.DemoStats
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class MatchFullDetailsMapper {

    companion object {
        fun map(matchStatsDto: MatchStatsDto, playerId: String, matchDemoDto: MatchDemoDto): MatchFullDetailsResponse {
            val matchFullDetails = matchStatsDto.matchFullDetails.first()

            val teamOne = getTeam(matchStatsDto, 0, getPlayers(matchStatsDto, 0))
            val teamTwo = getTeam(matchStatsDto, 1, getPlayers(matchStatsDto, 1))

            return matchFullDetails.roundStatsDto.let {
                MatchFullDetailsResponse(
                        matchFullDetails.matchId,
                        it.map,
                        it.score,
                        it.roundsCount.toInt(),
                        listOf(teamOne, teamTwo),
                        it.winnerTeamId,
                        getResult(playerId, listOf(teamOne, teamTwo), it.winnerTeamId),
                        getDemo(matchDemoDto),
                        DemoStatus.NO_ACTION.name)
            }
        }

        private fun getDemo(matchDemoDto: MatchDemoDto?): String? {
            return matchDemoDto?.urls?.let { if (it.isNotEmpty()) it.first() else null }
        }

        private fun getPlayerFromPlayerDto(playerDto: PlayerDto): Player {
            return Player(
                    playerDto.playerId, playerDto.nickname,
                    playerDto.playerStats.let {
                        PlayerStats(
                                it.kills.toInt(),
                                it.assists.toInt(),
                                it.deaths.toInt(),
                                it.headshots.toInt(),
                                it.headshotPercentage.toInt(),
                                mapKdRatio(playerDto),
                                it.krRatio.toBigDecimal(),
                                it.mvps.toInt(),
                                it.tripleKills.toInt(),
                                it.quadroKills.toInt(),
                                it.pentaKills.toInt())
                    })
        }

        private fun mapKdRatio(playerDto: PlayerDto): BigDecimal {
            val kdRatio = playerDto.playerStats.kdRatio.toBigDecimal()
            return if (kdRatio.compareTo(ZERO) == 0) playerDto.playerStats.kills.toBigDecimal() else kdRatio
        }

        private fun getTeam(matchStatsDto: MatchStatsDto, teamNumber: Int, players: Set<Player>): Team {
            return matchStatsDto.matchFullDetails.first().teams[teamNumber].let {
                Team(it.teamId, it.teamStats.teamName, players)
            }
        }

        private fun getPlayers(matchStatsDto: MatchStatsDto, teamNumber: Int): Set<Player> {
            return matchStatsDto.matchFullDetails.first().teams[teamNumber].players.map {
                getPlayerFromPlayerDto(it)
            }.toSet()
        }

        private fun getResult(playerId: String, teams: List<Team>, winnerTeam: String): MatchResult {
            return if (teams.first { t -> t.teamId == winnerTeam }.players.any { p -> p.playerId == playerId })
                MatchResult.WIN else MatchResult.LOSS
        }
    }
}
