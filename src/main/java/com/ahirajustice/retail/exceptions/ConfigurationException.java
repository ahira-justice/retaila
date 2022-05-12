package com.ahirajustice.retail.exceptions;

public class ConfigurationException extends ApplicationDomainException {

    public ConfigurationException() {
        super("Invalid system configuration", "NotImplemented", 501);
    }

    public ConfigurationException(String message) {
        super(message, "NotImplemented", 501);
    }

}
