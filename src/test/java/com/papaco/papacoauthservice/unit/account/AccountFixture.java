package com.papaco.papacoauthservice.unit.account;

import com.papaco.papacoauthservice.account.domain.Account;
import com.papaco.papacoauthservice.account.domain.AccountOutbox;
import com.papaco.papacoauthservice.account.domain.AccountRole;
import com.papaco.papacoauthservice.account.domain.event.AccountCreatedEvent;
import com.papaco.papacoauthservice.account.domain.event.EventType;

public class AccountFixture {
    public static final String USERNAME = "123456";
    public static final String NAME = "mandykr";
    public static final String EMAIL = "mandykr@gmail.com";
    public static final String USER_ROLE_KEY = AccountRole.USER.getKey();

    public static final Account ACCOUNT;
    public static final AccountCreatedEvent CREATED_EVENT;
    public static final AccountOutbox CREATED_OUTBOX;

    static {
        ACCOUNT = Account.create(USERNAME, NAME, EMAIL);
        CREATED_EVENT = new AccountCreatedEvent(EventType.CREATED, 1L, USERNAME, NAME, EMAIL, USER_ROLE_KEY);
        CREATED_OUTBOX = new AccountOutbox(1L, AccountCreatedEvent.class.getSimpleName(), EventType.CREATED, "");
    }
}
