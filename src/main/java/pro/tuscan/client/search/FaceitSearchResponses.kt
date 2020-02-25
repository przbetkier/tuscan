package pro.tuscan.client.search

import com.fasterxml.jackson.annotation.JsonProperty

data class FaceitSearchDTO(val payload: Payload)

data class Payload(@JsonProperty("players") val players: Players)

data class Players(@JsonProperty("results") val results: List<PlayerSearch>)

data class PlayerSearch(@JsonProperty("nickname") val nickname: String,
                        @JsonProperty("country") val country: String,
                        @JsonProperty("avatar") val avatarUrl: String?,
                        @JsonProperty("verified") val isVerified: Boolean,
                        @JsonProperty("games") val games: List<SimpleGame>) {

    fun hasCsgoGame(): Boolean {
        return games.stream().filter { it.name == "csgo" }.findAny().isPresent
    }

    fun getCsgoGame(): SimpleGame {
        return games.stream().filter { it.name == "csgo" }.findAny().get()
    }
}

data class SimpleGame(@JsonProperty("name") val name: String,
                      @JsonProperty("skill_level") val lvl: Int)
