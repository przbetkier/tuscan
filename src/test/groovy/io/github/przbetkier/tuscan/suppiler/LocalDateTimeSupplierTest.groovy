package io.github.przbetkier.tuscan.suppiler

import io.github.przbetkier.tuscan.supplier.LocalDateTimeSupplier
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class LocalDateTimeSupplierTest extends Specification {

    @Subject
    LocalDateTimeSupplier localDateTimeSupplier = new LocalDateTimeSupplier()

    def "should return now value"() {
        when:
        def result = localDateTimeSupplier.get()

        then:
        assert result instanceof LocalDateTime
        result.year == LocalDateTime.now().year
        result.month == LocalDateTime.now().month
        result.dayOfMonth == LocalDateTime.now().dayOfMonth
    }
}
