package io.github.przbetkier.tuscan.domain.player

import spock.lang.Specification

import static integration.common.MockedPlayer.PLAYER_ID
import static io.github.przbetkier.tuscan.client.player.Membership.FREE
import static io.github.przbetkier.tuscan.common.SamplePlayerDetails.simple
import static io.github.przbetkier.tuscan.common.SamplePlayerDetails.withoutCsGoGame
import static io.github.przbetkier.tuscan.domain.player.PlayerDetailsMapper.mapToPlayerDetailsResponse

class PlayerDetailsMapperTest extends Specification {

    def "should map player details dto to player details response"() {
        given:
        def playerId = PLAYER_ID
        def playerDetails = simple(playerId)

        when:
        def result = mapToPlayerDetailsResponse(playerDetails)

        then:
        with(result) {
            playerId == playerDetails.playerId
            nickname == playerDetails.nickname
            avatarUrl == playerDetails.avatarUrl
            country == playerDetails.country
            gameDetails.level == playerDetails.games.csgo.level
            gameDetails.faceitElo == playerDetails.games.csgo.faceitElo
            gameDetails.region == playerDetails.games.csgo.region
            membership == FREE
            !ban.active
        }
    }

    def "should map player details without csgo game"() {
        given:
        def playerDetails = withoutCsGoGame()

        when:
        def result = mapToPlayerDetailsResponse(playerDetails)

        then:
        result.gameDetails == null
    }
}
