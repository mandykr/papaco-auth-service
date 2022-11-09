package com.papaco.papacoauthservice.unit.account.application;

import com.papaco.papacoauthservice.account.application.OutboxMapper;
import com.papaco.papacoauthservice.account.application.exception.OutboxCreateFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.papaco.papacoauthservice.unit.account.AccountFixture.CREATED_EVENT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OutboxMapperTest {
    private OutboxMapper outboxMapper;

    @BeforeEach
    void setUp() {
        outboxMapper = new OutboxMapper();
    }

    @DisplayName("Outbox의 기본 생성자가 없으면 예외가 발생한다")
    @Test
    void noArgsConstructor() {
        assertThatThrownBy(() -> outboxMapper.createOutbox(FakeOutbox.class, CREATED_EVENT))
                .isInstanceOf(OutboxCreateFailedException.class);
    }
}
