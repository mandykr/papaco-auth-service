package com.papaco.papacoauthservice.account.application.message;

import com.papaco.papacoauthservice.account.domain.Outbox;

import java.util.List;

public interface AccountProducer {
    void send(List<Outbox> outboxes);
}
