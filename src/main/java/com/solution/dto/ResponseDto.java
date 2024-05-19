package com.solution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> {
    private List<String> responseMessage;
    private boolean isError;
    private T response;

    public ResponseDto(String responseMessage, boolean isError, T response) {
        this.responseMessage = Arrays.asList(responseMessage);
        this.isError = isError;
        this.response = response;
    }

    public ResponseDto(String responseMessage, boolean isError) {
        this.responseMessage = Arrays.asList(responseMessage);
        this.isError = isError;
    }
}
