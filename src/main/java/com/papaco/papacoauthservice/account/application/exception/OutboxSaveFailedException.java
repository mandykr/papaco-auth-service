package com.papaco.papacoauthservice.account.application.exception;

public class OutboxSaveFailedException extends RuntimeException {
    public OutboxSaveFailedException(Throwable cause) {
        super(cause);
    }
}
