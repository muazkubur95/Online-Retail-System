package miu.edu.onlineRetailSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                       WebRequest webRequest) {
        ErroDetails ed = new ErroDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );

        return new ResponseEntity<>(ed, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErroDetails> handleEmailAlreadyExistException(EmailAlreadyExistException exception,
                                                                        WebRequest webRequest) {
        ErroDetails ed = new ErroDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "EMAIL_EXISTS"
        );

        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }

    // Exception: since this is the super class of all exceptions,
    // this exception will be thrown when no other exception matched
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDetails> handleException(Exception exception,
                                                       WebRequest webRequest) {
        ErroDetails ed = new ErroDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL SERVER ERROR!"
        );

        return new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
