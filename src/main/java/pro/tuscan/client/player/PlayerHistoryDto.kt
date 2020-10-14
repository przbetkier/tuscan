package pro.tuscan.client.player

import com.fasterxml.jackson.annotation.JsonProperty

data class MatchHistoryDto(val elo: String?,
                           val matchId: String,
                           val date: Long,
                           @param:JsonProperty("gameMode") val mode: String,
                           @param:JsonProperty("c2") val kdRatio: String,
                           @param:JsonProperty("c4") val hsPercentage: String) {

    // FIXME: Should be redundant once PlayerHistoryMapper will be migrated to .kt
    fun hasElo(): Boolean {
        return elo != null
    }
}
