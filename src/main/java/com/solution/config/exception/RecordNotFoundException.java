package com.solution.config.exception;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends BaseRuntimeException {
    public RecordNotFoundException(String s) {
        super(s);
    }
    public RecordNotFoundException(String s, HttpStatus httpStatus){
        super(s,httpStatus);
    }
}
