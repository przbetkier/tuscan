package io.github.przbetkier.tuscan.domain.stats;

import io.github.przbetkier.tuscan.exception.DomainException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

class DemoStatsNotFoundException extends DomainException {

    DemoStatsNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
