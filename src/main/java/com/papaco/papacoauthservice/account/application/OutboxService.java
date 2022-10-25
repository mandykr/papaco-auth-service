package com.papaco.papacoauthservice.account.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papaco.papacoauthservice.account.application.exception.OutboxSaveFailedException;
import com.papaco.papacoauthservice.account.domain.Outbox;
import com.papaco.papacoauthservice.account.domain.event.AccountEvent;
import com.papaco.papacoauthservice.account.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(AccountEvent accountEvent) {
        try {
            String payload = objectMapper.writeValueAsString(accountEvent);
            Outbox outbox = new Outbox(accountEvent.getId(), accountEvent.getClass().getSimpleName(), accountEvent.getEventType(), payload);
            outboxRepository.save(outbox);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            log.error("accountEvent={}", accountEvent, e);
            throw new OutboxSaveFailedException(e);
        }
    }
}
