package com.papaco.papacoauthservice.account.domain.event;

import com.papaco.papacoauthservice.account.domain.Account;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountEventMapper {
    private Account account;
    private EventType eventType;

    public AccountEvent toAccountEvent() {
        switch (eventType) {
            case CREATED:
                return AccountCreatedEvent.of(account);
            case UPDATED:
                return AccountUpdatedEvent.of(account);
            default:
                throw new IllegalArgumentException();
        }
    }
}
