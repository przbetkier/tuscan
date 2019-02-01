package io.github.przbetkier.tuscan.domain.player

import io.github.przbetkier.tuscan.client.player.Membership
import io.github.przbetkier.tuscan.common.SamplePlayerDetails
import spock.lang.Specification

class PlayerDetailsMapperTest extends Specification {

    def "should map player details dto to player details response"() {
        given:
        def playerId = "playerId-1"
        def playerDetails = SamplePlayerDetails.simple(playerId)

        when:
        def result = PlayerDetailsMapper.mapToPlayerDetailsResponse(playerDetails)

        then:
        result.playerId == playerDetails.playerId
        result.nickname == playerDetails.nickname
        result.avatarUrl == playerDetails.avatarUrl
        result.country == playerDetails.country
        result.gameDetails.level == playerDetails.games.csgo.level
        result.gameDetails.faceitElo == playerDetails.games.csgo.faceitElo
        result.gameDetails.region == playerDetails.games.csgo.region
        result.membership == Membership.FREE
        result.ban.active == false
    }

    def "should map player details without csgo game"() {
        given:
        def playerDetails = SamplePlayerDetails.withoutCsGoGame()

        when:
        def result = PlayerDetailsMapper.mapToPlayerDetailsResponse(playerDetails)

        then:
        result.gameDetails == null
    }
}
