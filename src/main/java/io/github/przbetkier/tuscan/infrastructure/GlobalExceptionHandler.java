package io.github.przbetkier.tuscan.infrastructure;

import io.github.przbetkier.tuscan.domain.match.MatchNotFoundException;
import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import io.github.przbetkier.tuscan.exception.FaceitServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { PlayerNotFoundException.class, MatchNotFoundException.class })
    public ResponseEntity handleEntityNotFoundExceptions(Exception ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(value = {FaceitServerException.class})
    public ResponseEntity handleInternalServerErrors(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
