package io.github.przbetkier.tuscan.infrastructure;

import io.github.przbetkier.tuscan.domain.player.exception.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { PlayerNotFoundException.class })
    public ResponseEntity handleException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
