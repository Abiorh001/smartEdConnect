package com.abiorh.smartEdConnect.globalConfig.customException;


import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ErrorResponse {
    private String error;
    private String message;
    private OffsetDateTime timestamp;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = OffsetDateTime.now();
    }


}
