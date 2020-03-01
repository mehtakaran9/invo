package com.invo.dto;

import lombok.Data;

@Data
public class Response<T> {
    private String errorCode;
    private String errorMessage;
    private T result;
    private boolean success;
}
