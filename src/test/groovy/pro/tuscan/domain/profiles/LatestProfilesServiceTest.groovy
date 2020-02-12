package pro.tuscan.domain.profiles

import integration.common.request.LatestProfileSampleRequest
import pro.tuscan.common.SampleLatestProfile
import pro.tuscan.supplier.InstantSupplier
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.ZoneOffset

class LatestProfilesServiceTest extends Specification {

    InstantSupplier instantSupplier = Mock(InstantSupplier)
    LatestProfileRepository latestProfileRepository = Mock(LatestProfileRepository)

    @Subject
    LatestProfileService latestProfileService = new LatestProfileService(instantSupplier, latestProfileRepository)

    def "should save new player profile from request"() {
        given:
        def request = LatestProfileSampleRequest.simple()
        latestProfileRepository.findById(request.nickname) >> Mono.empty()


        def now = LocalDateTime.of(2018, 9, 26, 23, 30).toInstant(ZoneOffset.UTC)
        instantSupplier.get() >> now

        when:
        latestProfileService.save(request).block()

        then:
        1 * latestProfileRepository.save(_) >> Mono.just(SampleLatestProfile.simple(request.nickname))
    }

    def "should update player if is saved in database"() {
        given:
        def nickname = "player-1"
        def date = LocalDateTime.of(2018, 9, 26, 23, 30).toInstant(ZoneOffset.UTC)
        def request = LatestProfileSampleRequest.simple(nickname)
        def savedProfile = SampleLatestProfile.simple(nickname, date)

        instantSupplier.get() >> date
        latestProfileRepository.findById(nickname) >> Mono.just(savedProfile)

        when:
        latestProfileService.save(request).block()

        then:
        1 * latestProfileRepository.save(_) >> Mono.just(SampleLatestProfile.simple(nickname, date))
    }
}
