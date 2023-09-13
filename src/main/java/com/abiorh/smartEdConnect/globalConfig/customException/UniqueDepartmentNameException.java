package com.abiorh.smartEdConnect.globalConfig.customException;

import lombok.Getter;

@Getter
public class UniqueDepartmentNameException extends  Exception{

    private final String message;


    public UniqueDepartmentNameException(String message) {
        super(message);
        this.message = message;
    }
}
