package pro.tuscan.domain.stats;

import pro.tuscan.exception.DomainException;
import org.springframework.http.HttpStatus;

class DemoStatsException extends DomainException {

    public DemoStatsException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
