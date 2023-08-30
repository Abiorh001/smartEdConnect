package com.abiorh.smartEdConnect.student.customException;

import lombok.Getter;

@Getter
public class UniqueEmailException extends Exception {

    private final String message;

    public UniqueEmailException(String message) {
        super(message);
        this.message = message;
    }

}
