package com.abiorh.smartEdConnect.globalConfig.customException;

import lombok.Getter;

@Getter
public class UniqueSchoolNameException extends Exception{

    private final String message;

    public UniqueSchoolNameException(String message){
        super(message);
        this.message = message;
    }
}
