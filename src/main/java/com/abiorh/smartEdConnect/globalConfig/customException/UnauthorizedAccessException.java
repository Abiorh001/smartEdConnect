package com.abiorh.smartEdConnect.globalConfig.customException;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends Exception{
    private final String message;

    public UnauthorizedAccessException(String message) {
        super(message);
        this.message = message;
    }
}