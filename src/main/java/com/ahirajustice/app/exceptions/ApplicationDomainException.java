package com.ahirajustice.app.exceptions;

import com.ahirajustice.app.viewmodels.error.ErrorResponse;

public class ApplicationDomainException extends Exception {

    private String code;
    private int statusCode;

    public ApplicationDomainException() {
    }

    public ApplicationDomainException(String message) {
        super(message);
    }

    public ApplicationDomainException(String message, Throwable innerException) {
        super(message, innerException);
    }

    public ApplicationDomainException(String message, String code, int statusCode) {
        super(message);

        this.code = code;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return String.format("Error: \n\nStatusCode: %d\n\nCode: %s\n\nMessage: %s", getStatusCode(), getCode(),
                getMessage());
    }

    public ErrorResponse toErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(getCode());
        errorResponse.setMessage(getMessage());

        return errorResponse;
    }
}
