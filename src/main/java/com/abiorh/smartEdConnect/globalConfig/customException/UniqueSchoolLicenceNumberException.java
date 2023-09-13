package com.abiorh.smartEdConnect.globalConfig.customException;

import lombok.Getter;

@Getter
public class UniqueSchoolLicenceNumberException extends Exception{

    private final String message;

    public UniqueSchoolLicenceNumberException(String message){
        super(message);
        this.message = message;
    }
}
