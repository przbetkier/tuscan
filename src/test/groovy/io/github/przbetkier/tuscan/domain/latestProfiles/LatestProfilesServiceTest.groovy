package io.github.przbetkier.tuscan.domain.latestProfiles

import io.github.przbetkier.tuscan.common.SampleLatestProfile
import io.github.przbetkier.tuscan.common.response.SamplePlayerCsgoStats
import io.github.przbetkier.tuscan.common.response.SamplePlayerDetailsResponse
import io.github.przbetkier.tuscan.supplier.LocalDateTimeSupplier
import io.github.przbetkier.tuscan.config.properties.LatestProfilesProperties
import io.github.przbetkier.tuscan.domain.player.FaceitPlayerClient
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class LatestProfilesServiceTest extends Specification {

    LatestProfileRepository latestProfileRepository = Mock(LatestProfileRepository)
    FaceitPlayerClient faceitPlayerClient = Mock(FaceitPlayerClient)
    LocalDateTimeSupplier localDateTimeSupplier = Mock(LocalDateTimeSupplier)
    LatestProfilesProperties latestProfilesProperties = Mock(LatestProfilesProperties)

    @Subject
    LatestProfileService latestProfileService =
            new LatestProfileService(latestProfileRepository, faceitPlayerClient, localDateTimeSupplier, latestProfilesProperties)

    def "should save a player"() {
        given:
        latestProfilesProperties.getMaxSize() >> 4

        def nickname = "player-1"
        latestProfileRepository.findById(nickname) >> Optional.empty()
        def details = SamplePlayerDetailsResponse.simple(nickname)
        faceitPlayerClient.getPlayerDetails(nickname) >> details

        def stats = SamplePlayerCsgoStats.simple()
        faceitPlayerClient.getPlayerCsgoStats(details.playerId) >> stats

        latestProfileRepository.findAllByOrderByCreatedOnDesc() >> []

        def now = LocalDateTime.of(2018, 9, 26, 23, 30)
        localDateTimeSupplier.get() >> now

        when:
        latestProfileService.save(nickname)

        then:
        1 * latestProfileRepository.save(_ as LatestProfile)
        0 * latestProfileRepository.deleteAll()
    }

    def "should save a player update database"() {
        given:
        latestProfilesProperties.getMaxSize() >> 4

        def nickname = "player-1"
        latestProfileRepository.findById(nickname) >> Optional.empty()
        def details = SamplePlayerDetailsResponse.simple(nickname)
        faceitPlayerClient.getPlayerDetails(nickname) >> details

        def stats = SamplePlayerCsgoStats.simple()
        faceitPlayerClient.getPlayerCsgoStats(details.playerId) >> stats

        latestProfileRepository.findAllByOrderByCreatedOnDesc() >> listOfPlayersPlayers(4)

        def now = LocalDateTime.of(2018, 9, 26, 23, 30)
        localDateTimeSupplier.get() >> now

        when:
        latestProfileService.save(nickname)

        then:
        1 * latestProfileRepository.save({ it.nickname == nickname } as LatestProfile)
        0 * latestProfileRepository.deleteAll(_ as List<LatestProfile>)
        0 * latestProfileRepository.saveAll(_ as List<LatestProfile>)
    }

    def "should only update a player in database"() {
        given:
        latestProfilesProperties.getMaxSize() >> 4

        def nickname = "player-1"
        def savedProfile = SampleLatestProfile.simple(nickname)

        latestProfileRepository.findById(nickname) >> Optional.of(savedProfile)
        latestProfileRepository.findAllByOrderByCreatedOnDesc() >> listOfPlayersPlayers(4)

        def now = LocalDateTime.of(2018, 9, 26, 23, 30)
        localDateTimeSupplier.get() >> now

        when:
        latestProfileService.save(nickname)

        then:
        1 * latestProfileRepository.save({ it.nickname == nickname } as LatestProfile)
        0 * latestProfileRepository.deleteAll()
        0 * latestProfileRepository.saveAll(_ as List<LatestProfile>)
    }

    def "should save a player and trim the player profiles list in database"() {
        given:
        latestProfilesProperties.getMaxSize() >> 4

        def nickname = "player-1"
        def savedProfile = SampleLatestProfile.simple(nickname)

        latestProfileRepository.findById(nickname) >> Optional.of(savedProfile)
        latestProfileRepository.findAllByOrderByCreatedOnDesc() >> listOfPlayersPlayers(7)

        def now = LocalDateTime.of(2018, 9, 26, 23, 30)
        localDateTimeSupplier.get() >> now

        when:
        latestProfileService.save(nickname)

        then:
        1 * latestProfileRepository.save({ it.nickname == nickname } as LatestProfile)
        1 * latestProfileRepository.deleteAll()
        1 * latestProfileRepository.saveAll(_ as List<LatestProfile>)
    }

    def "should not save a player and not update database"() {
        given:
        latestProfilesProperties.getMaxSize() >> 4

        def nickname = "player-1"
        latestProfileRepository.findById(nickname) >> Optional.empty()
        faceitPlayerClient.getPlayerDetails(nickname) >> { throw new PlayerNotFoundException("player not found") }

        when:
        latestProfileService.save(nickname)

        then:
        thrown(PlayerNotFoundException)
        0 * latestProfileRepository.save(_ as LatestProfile)
        0 * latestProfileRepository.deleteAll()
        0 * latestProfileRepository.saveAll(_ as List<LatestProfile>)
    }

    static List<LatestProfile> listOfPlayersPlayers(int players) {
        def list = []
        for (int i = 0; i < players; i++) {
            list.add(SamplePlayerCsgoStats.simple())
        }
        return list
    }
}
