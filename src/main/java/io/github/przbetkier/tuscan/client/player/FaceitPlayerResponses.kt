package io.github.przbetkier.tuscan.client.player

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.github.przbetkier.tuscan.infrastructure.ZonedDateTimeDeserializer
import java.time.ZonedDateTime

data class Csgo(
        @JsonProperty("faceit_elo") val faceitElo: Int?,
        @JsonProperty("skill_level") val level: Int?,
        @JsonProperty("region") val region: String,
        @JsonProperty("game_player_id") val steamId: String
)

data class Games(
        @JsonProperty("csgo") val csgo: Csgo?)

data class Position(
        @JsonProperty("position") val position: String
)

data class Lifetime(
        @JsonProperty("Average Headshots %") val headshotPercentage: String,
        @JsonProperty("Matches") val matches: String,
        @JsonProperty("Average K/D Ratio") val kdRatio: String,
        @JsonProperty("Win Rate %") val winRate: String,
        @JsonProperty("Current Win Streak") val currentWinStreak: Int?,
        @JsonProperty("Longest Win Streak") val longestWinStreak: Int?
)

data class MapStatistics(
        @JsonProperty("Matches") val matches: String,
        @JsonProperty("Average K/D Ratio") val kdRatio: String,
        @JsonProperty("Average Kills") val averageKills: String,
        @JsonProperty("Average Headshots %") val hsPercentage: String,
        @JsonProperty("Win Rate %") val winPercentage: String,
        @JsonProperty("Wins") val wins: String,
        @JsonProperty("Triple Kills") val tripleKills: String,
        @JsonProperty("Quadro Kills") val quadroKills: String,
        @JsonProperty("Penta Kills") val pentaKills: String
)

data class PlayerStats(
        @JsonProperty("lifetime") val lifetime: Lifetime,
        @JsonProperty("segments") val segments: List<Segment>
)

data class Segment(
        @JsonProperty("label") val name: String,
        @JsonProperty("stats") val mapStatistics: MapStatistics,
        @JsonProperty("mode") val mode: String
)

data class PlayerDetails(
        @JsonProperty("player_id") val playerId: String,
        @JsonProperty("nickname") val nickname: String,
        @JsonProperty("games") val games: Games,
        @JsonProperty("avatar") val avatarUrl: String,
        @JsonProperty("country") val country: String,
        @JsonProperty("memberships") val memberships: List<String>,
        @JsonProperty("bans") val bans: List<Ban>
) {

    fun hasCsgoGame(): Boolean {
        return this.games.csgo != null
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ban(
        @JsonDeserialize(using = ZonedDateTimeDeserializer::class)
        @JsonProperty("created_at") val createdAt: ZonedDateTime,
        @JsonDeserialize(using = ZonedDateTimeDeserializer::class)
        @JsonProperty("starts_at") val startsAt: ZonedDateTime,
        @JsonProperty("reason") val reason: String
)


