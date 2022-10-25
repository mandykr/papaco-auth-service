package com.papaco.papacoauthservice.study;

import com.papaco.papacoauthservice.account.application.LoginAccountService;
import com.papaco.papacoauthservice.account.domain.event.AccountEvent;
import com.papaco.papacoauthservice.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class TransactionalEventListenerTest {
    @Autowired
    private LoginAccountService loginAccountService;

    @Autowired
    private AccountRepository accountRepository;

    Map<String, Object> accountAttributes;

    @BeforeEach
    void setUp() {
        accountAttributes = new HashMap<>();
        accountAttributes.put("userName", "123456");
        accountAttributes.put("name", "mandykr");
        accountAttributes.put("email", "mandykr@gmail.com");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        OutboxService outboxServiceTest() {
            return new OutboxService();
        }
    }

    @Test
    void rollback() {
        assertThatThrownBy(() -> loginAccountService.saveOrUpdateAccount(accountAttributes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("throw JsonProcessingException");
    }

    @Test
    void commit() {
        loginAccountService.saveOrUpdateAccount(accountAttributes);
        assertThat(accountRepository.findAll()).isNotEmpty();
    }

    static class OutboxService {
//        @TransactionalEventListener
        @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
        public void saveOutbox(AccountEvent accountEvent) {
            System.out.println("OutboxServiceTest accountEvent = " + accountEvent);
            throw new IllegalArgumentException("throw JsonProcessingException");
            // account 를 저장하고 발행한 accountEvent 를 outbox 테이블에 저장해야 한다.
            // account 는 정상적으로 저장됐는데 outbox 를 저장하는 과정에서 예외가 발생하면 어떻게 해야할까?
            // outboxRepository.save(accountEvent);
        }
    }
}
