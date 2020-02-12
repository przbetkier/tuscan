package pro.tuscan.domain.match;

import pro.tuscan.exception.DomainException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MatchNotFoundException extends DomainException {

    public MatchNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
