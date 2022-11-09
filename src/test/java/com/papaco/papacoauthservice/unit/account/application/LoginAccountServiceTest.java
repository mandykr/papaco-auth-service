package com.papaco.papacoauthservice.unit.account.application;

import com.papaco.papacoauthservice.account.application.LoginAccountService;
import com.papaco.papacoauthservice.account.application.event.AccountEventPublisher;
import com.papaco.papacoauthservice.account.domain.Account;
import com.papaco.papacoauthservice.account.domain.LoginAccount;
import com.papaco.papacoauthservice.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.papaco.papacoauthservice.unit.account.AccountFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class LoginAccountServiceTest {
    private final AccountEventPublisher accountEventPublisher = mock(AccountEventPublisher.class);
    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private LoginAccountService loginAccountService;
    private Map<String, Object> accountAttributes;
    private Account account;

    @BeforeEach
    void setUp() {
        loginAccountService = new LoginAccountService(accountRepository, accountEventPublisher);
        accountAttributes = new HashMap<>();
        accountAttributes.put("registrationId", "github");
        accountAttributes.put("userName", USERNAME);
        accountAttributes.put("name", NAME);
        accountAttributes.put("email", EMAIL);
        account = Account.create(USERNAME, NAME, EMAIL);
    }

    @DisplayName("OAuth 로그인 정보로 계정을 저장한다")
    @Test
    void create() {
        given(accountRepository.findByUserName(USERNAME)).willReturn(Optional.empty());
        given(accountRepository.save(account)).willReturn(account);

        LoginAccount loginAccount = loginAccountService.saveOrUpdateAccount(accountAttributes);

        assertThat(loginAccount.getUserName()).isEqualTo(USERNAME);
        verify(accountEventPublisher, times(1)).publishCreatedEvent(any(Account.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @DisplayName("OAuth 로그인 정보로 계정을 업데이트 한다")
    @Test
    void update() {
        given(accountRepository.findByUserName(USERNAME)).willReturn(Optional.of(account));

        LoginAccount loginAccount = loginAccountService.saveOrUpdateAccount(accountAttributes);

        assertThat(loginAccount.getUserName()).isEqualTo(USERNAME);
        verify(accountEventPublisher, times(1)).publishUpdatedEvent(any(Account.class));
    }
}
