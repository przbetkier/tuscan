package io.github.przbetkier.tuscan.domain.player.exception;

import io.github.przbetkier.tuscan.exception.DomainException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PlayerNotFoundException extends DomainException {

    public PlayerNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
