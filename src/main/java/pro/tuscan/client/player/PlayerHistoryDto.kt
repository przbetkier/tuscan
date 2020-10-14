package pro.tuscan.client.player

import com.fasterxml.jackson.annotation.JsonProperty

data class MatchHistoryDto(val elo: String?,
                           val matchId: String,
                           val date: Long,
                           @JsonProperty("gameMode") val mode: String,
                           @JsonProperty("c2") val kdRatio: String,
                           @JsonProperty("c4") val hsPercentage: String) {

    // FIXME: Should be redundant once PlayerHistoryMapper will be migrated to .kt
    fun hasElo(): Boolean {
        return elo != null
    }
}
