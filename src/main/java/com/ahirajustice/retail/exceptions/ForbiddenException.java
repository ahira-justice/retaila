package com.ahirajustice.retail.exceptions;

public class ForbiddenException extends ApplicationDomainException {

    public ForbiddenException() {
        super("Unauthorized: user is not allowed to access this resource", "Forbidden", 403);
    }

    public ForbiddenException(String username) {
        super(String.format("Unauthorized: %s is not allowed to access this resource", username), "Forbidden", 403);
    }

}
