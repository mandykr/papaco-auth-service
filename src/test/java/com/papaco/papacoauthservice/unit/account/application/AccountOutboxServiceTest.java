package com.papaco.papacoauthservice.unit.account.application;

import com.papaco.papacoauthservice.account.application.AccountOutboxService;
import com.papaco.papacoauthservice.account.domain.AccountOutbox;
import com.papaco.papacoauthservice.account.domain.repository.AccountOutboxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.papaco.papacoauthservice.unit.account.AccountFixture.CREATED_EVENT;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountOutboxServiceTest {
    @Autowired
    private AccountOutboxRepository accountOutboxRepository;
    private AccountOutboxService accountOutboxService;

    @BeforeEach
    void setUp() {
        accountOutboxService = new AccountOutboxService(accountOutboxRepository);
    }

    @DisplayName("Account Outbox를 저장한다")
    @Test
    void saveOutbox() {
        accountOutboxService.saveOutbox(CREATED_EVENT);

        AccountOutbox accountOutbox = accountOutboxRepository.findById(1L).get();
        assertThat(accountOutbox.getAggregateId()).isEqualTo(CREATED_EVENT.getAggregateId());
    }
}
