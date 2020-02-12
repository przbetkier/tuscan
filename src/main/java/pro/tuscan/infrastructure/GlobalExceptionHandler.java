package pro.tuscan.infrastructure;

import pro.tuscan.exception.DomainException;
import pro.tuscan.supplier.InstantSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
class GlobalExceptionHandler extends DefaultErrorAttributes {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final InstantSupplier instantSupplier;

    GlobalExceptionHandler(InstantSupplier instantSupplier) {this.instantSupplier = instantSupplier;}

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException exc, ServerWebExchange exchange) {
        final HttpStatus status = exc.getStatus();
        final ServerHttpRequest request = exchange.getRequest();
        logger.warn("Domain exception occurred", exc);
        return new ResponseEntity<>(new ErrorResponse(instantSupplier.get().toEpochMilli(),
                                                      request.getPath().value(),
                                                      status.value(),
                                                      status.getReasonPhrase(),
                                                      exc.getMessage()), status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exc, ServerWebExchange exchange) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ServerHttpRequest request = exchange.getRequest();
        logger.error("Unexpected error occurred", exc);
        return new ResponseEntity<>(new ErrorResponse(instantSupplier.get().toEpochMilli(),
                                                      request.getPath().value(),
                                                      status.value(),
                                                      status.getReasonPhrase(),
                                                      exc.getMessage()), status);
    }
}
