package pro.tuscan.domain.player.exception;

import pro.tuscan.exception.DomainException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PlayerNotFoundException extends DomainException {

    public PlayerNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
