package io.github.przbetkier.tuscan.supplier;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class InstantSupplier implements Supplier<Instant> {

    @Override
    public Instant get() {
        return Instant.now();
    }
}
