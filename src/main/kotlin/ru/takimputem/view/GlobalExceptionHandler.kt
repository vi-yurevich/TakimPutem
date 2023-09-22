package ru.takimputem.view

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.takimputem.view.exceptions.EventNotFoundException
import ru.takimputem.view.exceptions.ValidateDtoException
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    fun newResponseEntityWithErrorResponse(message: String?, status: HttpStatus): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                timestamp = LocalDateTime.now(),
                status = status.value(),
                message = message
            ),
            status
        )
    }

    @ExceptionHandler(value = [ValidateDtoException::class, IllegalArgumentException::class])
    fun handleValidateDtoException(ex: Exception): ResponseEntity<ErrorResponse> =
        newResponseEntityWithErrorResponse(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(EventNotFoundException::class)
    fun handleEventNotFoundException(ex: Exception): ResponseEntity<ErrorResponse> =
        newResponseEntityWithErrorResponse(ex.message, HttpStatus.NOT_FOUND)
}