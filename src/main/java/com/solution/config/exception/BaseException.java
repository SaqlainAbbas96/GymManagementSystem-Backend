package com.solution.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseException extends BaseRuntimeException {
    public BaseException(String s) {
        super(s);
    }
}
