package io.github.przbetkier.tuscan.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FaceitServerException extends DomainException {
    public FaceitServerException(String message) {
        super(INTERNAL_SERVER_ERROR, message);
    }
}
