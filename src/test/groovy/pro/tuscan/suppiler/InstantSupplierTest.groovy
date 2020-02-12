package pro.tuscan.suppiler

import pro.tuscan.supplier.InstantSupplier
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class InstantSupplierTest extends Specification {

    @Subject
    InstantSupplier instantSupplier = new InstantSupplier()

    def "should return now value"() {
        when:
        def result = instantSupplier.get()

        then:
        assert result instanceof Instant
        LocalDateTime.ofInstant(result, ZoneOffset.UTC).year == LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).year
        LocalDateTime.ofInstant(result, ZoneOffset.UTC).month == LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).month
        LocalDateTime.ofInstant(result, ZoneOffset.UTC).dayOfMonth == LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).dayOfMonth
    }
}
