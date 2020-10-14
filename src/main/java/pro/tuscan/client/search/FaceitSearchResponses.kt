package pro.tuscan.client.search

import com.fasterxml.jackson.annotation.JsonProperty

data class FaceitSearchDTO(val payload: Payload)

data class Payload(val players: Players)

data class Players(val results: List<PlayerSearch>)

data class PlayerSearch(val nickname: String,
                        val country: String,
                        @JsonProperty("avatar") val avatarUrl: String?,
                        @JsonProperty("verified") val isVerified: Boolean,
                        val games: List<SimpleGame>) {

    fun hasCsgoGame(): Boolean = games.any { it.name == "csgo" }

    // FIXME: May be removed once migrated PlayerSearchMapper to .kt
    fun getCsgoGame(): SimpleGame =
        games.stream().filter { it.name == "csgo" }.findAny().get()

}

data class SimpleGame(val name: String,
                      @JsonProperty("skill_level") val lvl: Int)
