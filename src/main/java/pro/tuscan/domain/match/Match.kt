package pro.tuscan.domain.match

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "matches")
data class Match @PersistenceConstructor constructor(
        @Id val matchId: String,
        val map: String,
        val score: String,
        val roundsCount: Int,
        val teams: List<MatchTeam>,
        val winnerTeam: String,
        val demoUrl: String?,
        val demoStatus: DemoStatus
)

data class MatchTeam(
        val teamId: String,
        val teamName: String,
        val players: Set<MatchPlayer>
)

data class MatchPlayer(
        val playerId: String,
        val nickname: String,
        val playerStats: MatchPlayerStats
)

data class MatchPlayerStats(
        val kills: Int,
        val assists: Int,
        val deaths: Int,
        val headshots: Int,
        val headshotPercentage: Int,
        val kdRatio: BigDecimal,
        val krRatio: BigDecimal,
        val mvps: Int,
        val tripleKills: Int,
        val quadroKills: Int,
        val pentaKills: Int
)

enum class DemoStatus {
    NO_ACTION, COMPUTING, PARSED
}

