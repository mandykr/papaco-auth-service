package com.papaco.papacoauthservice.account.infra;

import com.papaco.papacoauthservice.account.application.message.AccountProducer;
import com.papaco.papacoauthservice.account.domain.Outbox;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.core.SqsMessageHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccountSQSProducer implements AccountProducer {
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Transactional
    @Override
    public void send(List<Outbox> outboxes) {
        outboxes.forEach(outbox -> {
            Message<String> message = MessageBuilder.withPayload(outbox.getPayload())
                    .setHeader(SqsMessageHeaders.SQS_GROUP_ID_HEADER, String.valueOf(outbox.getAggregateId()))
                    .setHeader(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, String.valueOf(outbox.getId()))
                    .build();

            queueMessagingTemplate.send(endpoint, message);
            log.info("Message: {}, {}, {}", outbox.getAggregateId(), outbox.getId(), message);
        });
    }
}
