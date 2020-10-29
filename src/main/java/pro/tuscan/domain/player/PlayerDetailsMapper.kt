package pro.tuscan.domain.player

import pro.tuscan.adapter.api.GameDetails
import pro.tuscan.adapter.api.Membership
import pro.tuscan.adapter.api.PlayerDetailsResponse
import pro.tuscan.client.player.PlayerDetails

object PlayerDetailsMapper {

    @JvmStatic
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

    private fun mapToCsgoGameDetails(details: PlayerDetails): GameDetails? =
            details.games.csgo
                    ?.let { GameDetails(it.faceitElo, it.level, it.region, it.steamId) }
}
