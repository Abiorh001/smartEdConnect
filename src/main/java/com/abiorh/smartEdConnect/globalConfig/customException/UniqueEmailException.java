package com.abiorh.smartEdConnect.globalConfig.customException;

import lombok.Getter;

@Getter
public class UniqueEmailException extends Exception {

    private final String message;

    public UniqueEmailException(String message) {
        super(message);
        this.message = message;
    }

}
