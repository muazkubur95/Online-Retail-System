package miu.edu.onlineRetailSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class CustomerErrorException extends RuntimeException {
    private String errorMessage;

    public CustomerErrorException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
