package com.ahirajustice.retaila.exceptions;

public class UnauthorizedException extends ApplicationDomainException {

    public UnauthorizedException(String message) {
        super(message, "Unauthorized", 401);
    }

}
