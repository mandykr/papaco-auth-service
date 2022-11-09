package com.papaco.papacoauthservice.account.application.exception;

public class OutboxCreateFailedException extends RuntimeException {
    public OutboxCreateFailedException(Throwable cause) {
        super(cause);
    }
}
