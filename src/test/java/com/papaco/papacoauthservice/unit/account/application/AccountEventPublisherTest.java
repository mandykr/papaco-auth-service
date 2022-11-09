package com.papaco.papacoauthservice.unit.account.application;

import com.papaco.papacoauthservice.account.application.event.AccountEventPublisher;
import com.papaco.papacoauthservice.account.domain.event.AccountCreatedEvent;
import com.papaco.papacoauthservice.account.domain.event.AccountUpdatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static com.papaco.papacoauthservice.unit.account.AccountFixture.ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

@RecordApplicationEvents
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountEventPublisherTest {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ApplicationEvents events;

    private AccountEventPublisher accountEventPublisher;

    @BeforeEach
    void serUp() {
        accountEventPublisher = new AccountEventPublisher(eventPublisher);
    }

    @DisplayName("계정 생성 이벤트를 발행한다")
    @Test
    void createdEvent() {
        accountEventPublisher.publishCreatedEvent(ACCOUNT);

        int count = (int) events.stream(AccountCreatedEvent.class).count();
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("계정 생성 이벤트를 발행한다")
    @Test
    void updatedEvent() {
        accountEventPublisher.publishUpdatedEvent(ACCOUNT);

        int count = (int) events.stream(AccountUpdatedEvent.class).count();
        assertThat(count).isEqualTo(1);
    }
}
