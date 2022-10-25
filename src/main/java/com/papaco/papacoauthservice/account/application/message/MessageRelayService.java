package com.papaco.papacoauthservice.account.application.message;

import com.papaco.papacoauthservice.account.domain.Outbox;
import com.papaco.papacoauthservice.account.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MessageRelayService {
    private final OutboxRepository outboxRepository;
    private final AccountProducer accountProducer;

    @Value("${cdc.batch_size}")
    private int batchSize;

    @Scheduled(fixedDelayString = "${cdc.polling_ms}")
    public void sendAccountEvent() {
        List<Outbox> outboxes = outboxRepository.findAllByOrderByIdAsc(Pageable.ofSize(batchSize)).toList();
        accountProducer.send(outboxes);
        outboxRepository.deleteAllInBatch(outboxes);
    }
}
