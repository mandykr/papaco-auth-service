package com.papaco.papacoauthservice.unit.account.application.message;

import com.papaco.papacoauthservice.account.application.message.AccountProducer;
import com.papaco.papacoauthservice.account.application.message.MessageRelayService;
import com.papaco.papacoauthservice.account.domain.AccountOutbox;
import com.papaco.papacoauthservice.account.domain.repository.AccountOutboxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import static com.papaco.papacoauthservice.unit.account.AccountFixture.CREATED_OUTBOX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRelayServiceTest {
    @Autowired
    private AccountOutboxRepository outboxRepository;
    private AccountProducer accountProducer = mock(AccountProducer.class);
    private MessageRelayService messageRelayService;

    @BeforeEach
    void setUp() {
        messageRelayService = new MessageRelayService(outboxRepository, accountProducer);
        ReflectionTestUtils.setField(messageRelayService, "batchSize", 50);
    }

    @DisplayName("AccountOutbox를 읽어와 메시지를 전송한다")
    @Test
    void sendAccountEvent() {
        AccountOutbox save = outboxRepository.save(CREATED_OUTBOX);
        messageRelayService.sendAccountEvent();
        AccountOutbox outbox = outboxRepository.findById(save.getId()).get();
        assertAll(
                () -> assertThat(outbox.getAggregateId()).isEqualTo(CREATED_OUTBOX.getAggregateId()),
                () -> verify(accountProducer, times(1)).send(anyList()),
                () -> assertThat(outbox.isPublished()).isTrue()
        );
    }
}
