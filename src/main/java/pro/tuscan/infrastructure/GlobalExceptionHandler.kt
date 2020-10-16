package pro.tuscan.infrastructure

import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange
import pro.tuscan.exception.DomainException
import pro.tuscan.supplier.InstantSupplier

@ControllerAdvice
internal class GlobalExceptionHandler(private val instantSupplier: InstantSupplier) : DefaultErrorAttributes() {

    @ExceptionHandler(RuntimeException::class, DomainException::class)
    fun handleException(exception: Exception, exchange: ServerWebExchange): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error occurred", exception)
        return toErrorResponse(instantSupplier.get().toEpochMilli(), toStatus(exception), exchange, exception)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

        private fun toStatus(exc: Exception) =
                exc.let { if (it is DomainException) it.status else INTERNAL_SERVER_ERROR }

        private fun toErrorResponse(timestamp: Long, status: HttpStatus, exchange: ServerWebExchange, ex: Exception)
                : ResponseEntity<ErrorResponse> =
                ResponseEntity(ErrorResponse(
                        timestamp,
                        exchange.request.path.value(),
                        status.value(),
                        status.reasonPhrase,
                        ex.message ?: "Error occurred"), status)
    }
}

data class ErrorResponse(val timestamp: Long,
                         val path: String,
                         val status: Int,
                         val error: String,
                         val message: String)
