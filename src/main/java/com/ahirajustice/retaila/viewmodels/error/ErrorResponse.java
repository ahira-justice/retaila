package com.ahirajustice.retaila.viewmodels.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String code;
    private String message;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public String getTimestamp() {
        return this.timestamp.toString();
    }

}
