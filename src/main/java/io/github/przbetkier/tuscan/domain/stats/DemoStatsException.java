package io.github.przbetkier.tuscan.domain.stats;

import io.github.przbetkier.tuscan.exception.DomainException;
import org.springframework.http.HttpStatus;

class DemoStatsException extends DomainException {

    public DemoStatsException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
