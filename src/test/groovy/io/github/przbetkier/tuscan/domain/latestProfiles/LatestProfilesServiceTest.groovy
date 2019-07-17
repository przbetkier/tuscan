package io.github.przbetkier.tuscan.domain.latestProfiles

import io.github.przbetkier.tuscan.client.player.FaceitPlayerClient
import io.github.przbetkier.tuscan.common.SampleLatestProfile
import io.github.przbetkier.tuscan.common.response.SamplePlayerCsgoStats
import io.github.przbetkier.tuscan.common.response.SamplePlayerDetailsResponse
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException
import io.github.przbetkier.tuscan.supplier.LocalDateTimeSupplier
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class LatestProfilesServiceTest extends Specification {

    FaceitPlayerClient faceitPlayerClient = Mock(FaceitPlayerClient)
    LocalDateTimeSupplier localDateTimeSupplier = Mock(LocalDateTimeSupplier)
    LatestProfileRepository latestProfileRepository = Mock(LatestProfileRepository)

    @Subject
    LatestProfileService latestProfileService =
            new LatestProfileService(faceitPlayerClient, localDateTimeSupplier, latestProfileRepository)

    def "should call faceit player client and save new player"() {
        given:
        def nickname = "player-1"
        latestProfileRepository.findById(nickname) >> Mono.empty()
        def details = SamplePlayerDetailsResponse.simple(nickname)
        def stats = SamplePlayerCsgoStats.simple()

        def now = LocalDateTime.of(2018, 9, 26, 23, 30)
        localDateTimeSupplier.get() >> now

        when:
        latestProfileService.save(nickname).block()

        then:
        1 * latestProfileRepository.save(_) >> Mono.just(SampleLatestProfile.simple(nickname))
        1 * faceitPlayerClient.getPlayerDetails(nickname) >> Mono.just(details)
        1 * faceitPlayerClient.getPlayerCsgoStats(details.playerId) >> Mono.just(stats)
    }

    def "should only update a player in database"() {
        given:
        def nickname = "player-1"
        def date = LocalDateTime.of(2018, 9, 26, 23, 30)
        def savedProfile = SampleLatestProfile.simple(nickname, date)

        localDateTimeSupplier.get() >> date
        latestProfileRepository.findById(savedProfile.nickname) >> Mono.just(savedProfile)

        when:
        latestProfileService.save(nickname).block()

        then:
        1 * latestProfileRepository.save(_) >> Mono.just(savedProfile)
        0 * faceitPlayerClient.getPlayerDetails(nickname)
        0 * faceitPlayerClient.getPlayerCsgoStats(_ as String)
    }

    def "should not save a player and not update database"() {
        given:
        def nickname = "player-1"
        latestProfileRepository.findById(nickname) >> Mono.empty()
        faceitPlayerClient.getPlayerDetails(nickname) >> { throw new PlayerNotFoundException("player not found") }

        when:
        latestProfileService.save(nickname).block()

        then:
        thrown(PlayerNotFoundException)
        0 * latestProfileRepository.save(_ as LatestProfile)
    }
}
