package com.papaco.papacoauthservice.account.application;

import com.papaco.papacoauthservice.account.domain.AccountOutbox;
import com.papaco.papacoauthservice.account.domain.event.AccountEvent;
import com.papaco.papacoauthservice.account.domain.repository.AccountOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountOutboxService {
    private final AccountOutboxRepository accountOutboxRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(AccountEvent accountEvent) {
        OutboxMapper outboxMapper = new OutboxMapper();
        AccountOutbox outbox = outboxMapper.createOutbox(AccountOutbox.class, accountEvent);
        accountOutboxRepository.save(outbox);
    }
}
