package com.papaco.papacoauthservice.account.application.event;

import com.papaco.papacoauthservice.account.domain.Account;
import com.papaco.papacoauthservice.account.domain.event.AccountEventMapper;
import com.papaco.papacoauthservice.account.domain.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishEvent(Account account, EventType eventType) {
        AccountEventMapper eventMapper = new AccountEventMapper(account, eventType);
        eventPublisher.publishEvent(eventMapper.toAccountEvent());
    }

    public void publishUpdatedEvent(Account account) {
        publishEvent(account, EventType.UPDATED);
    }

    public void publishCreatedEvent(Account account) {
        publishEvent(account, EventType.CREATED);
    }
}
