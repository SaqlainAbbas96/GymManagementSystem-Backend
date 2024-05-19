package com.solution.config.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseRuntimeException extends RuntimeException {
    protected String message;
    protected HttpStatus httpStatus;

    public BaseRuntimeException(String message) {
        this.message = message;
    }

    public BaseRuntimeException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
