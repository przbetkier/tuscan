package pro.tuscan.supplier

import org.springframework.stereotype.Component
import java.time.Instant
import java.util.function.Supplier

@Component
class InstantSupplier : Supplier<Instant> {
    override fun get(): Instant = Instant.now()
}
