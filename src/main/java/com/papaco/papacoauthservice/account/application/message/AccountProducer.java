package com.papaco.papacoauthservice.account.application.message;

import com.papaco.papacoauthservice.account.domain.AccountOutbox;

import java.util.List;

public interface AccountProducer {
    void send(List<AccountOutbox> outboxes);
}
