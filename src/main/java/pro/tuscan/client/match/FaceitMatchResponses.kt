package pro.tuscan.client.match

import com.fasterxml.jackson.annotation.JsonProperty

data class SimpleMatchDto(
        @JsonProperty("match_id") val matchId: String,
        @JsonProperty("started_at") val startedAt: Long,
        @JsonProperty("finished_at") val finishedAt: Long
)

data class MatchesSimpleDetailsDto(
        @JsonProperty("items") val simpleMatchList: List<SimpleMatchDto>
) {
    val matchesCount: Int?

    init {
        this.matchesCount = this.simpleMatchList.size
    }
}

data class OpenMatchSimpleDetailsDto(
        @JsonProperty("matchId") val id: String,
        @JsonProperty("created_at") val startedAt: Long,
        @JsonProperty("date") val finishedAt: Long
)

data class MatchFullDetailsDto(
        @JsonProperty("match_id") val matchId: String,
        @JsonProperty("round_stats") val roundStatsDto: RoundStatsDto,
        @JsonProperty("teams") val teams: List<TeamDto>
)

data class MatchStatsDto(
        @JsonProperty("rounds") val matchFullDetails: List<MatchFullDetailsDto>
)

data class PlayerDto(
        @JsonProperty("player_id") val playerId: String,
        @JsonProperty("nickname") val nickname: String,
        @JsonProperty("player_stats") val playerStats: PlayerStatsDto
)

data class PlayerStatsDto(
        @JsonProperty("Kills") val kills: String,
        @JsonProperty("Assists") val assists: String,
        @JsonProperty("Deaths") val deaths: String,
        @JsonProperty("Headshot") val headshots: String,
        @JsonProperty("Headshots %") val headshotPercentage: String,
        @JsonProperty("K/D Ratio") val kdRatio: String,
        @JsonProperty("K/R Ratio") val krRatio: String,
        @JsonProperty("MVPs") val mvps: String,
        @JsonProperty("Penta Kills") val pentaKills: String,
        @JsonProperty("Quadro Kills") val quadroKills: String,
        @JsonProperty("Triple Kills") val tripleKills: String
)

data class RoundStatsDto(
        @JsonProperty("Map") var map: String?,
        @JsonProperty("Rounds") val roundsCount: String,
        @JsonProperty("Score") val score: String,
        @JsonProperty("Winner") val winnerTeamId: String
)

data class TeamDto(
        @JsonProperty("team_id") val teamId: String,
        @JsonProperty("team_stats") val teamStats: TeamStatsDto,
        @JsonProperty("players") val players: List<PlayerDto>
)

data class TeamStatsDto(
        @JsonProperty("Team") val teamName: String,
        @JsonProperty("Team Headshot") val headshotAvg: String
)

data class MatchDemoDto(
        @JsonProperty("demo_url") val urls: Collection<String>
)

