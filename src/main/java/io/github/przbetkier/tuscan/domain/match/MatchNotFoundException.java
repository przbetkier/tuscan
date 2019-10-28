package io.github.przbetkier.tuscan.domain.match;

import io.github.przbetkier.tuscan.exception.DomainException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MatchNotFoundException extends DomainException {

    public MatchNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
