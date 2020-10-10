package pro.tuscan.domain.player

import pro.tuscan.adapter.api.response.PlayerDetailsResponse
import pro.tuscan.adapter.api.response.dto.GameDetails
import pro.tuscan.client.player.Membership
import pro.tuscan.client.player.PlayerDetails

class PlayerDetailsMapper {

    companion object {
        fun mapToPlayerDetailsResponse(details: PlayerDetails): PlayerDetailsResponse =
                details.let {
                    PlayerDetailsResponse(
                            it.playerId,
                            it.nickname,
                            mapToCsgoGameDetails(it),
                            it.avatarUrl,
                            it.country,
                            it.memberships.first().let { Membership.valueOf(it.toUpperCase()) })
                }

        private fun mapToCsgoGameDetails(details: PlayerDetails): GameDetails? {
            return details.games.csgo
                    ?.let { GameDetails(it.faceitElo, it.level, it.region, it.steamId) }
        }
    }
}
