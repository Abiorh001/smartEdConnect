package com.abiorh.smartEdConnect.student.customException;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception{
    private final String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
