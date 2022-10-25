package com.papaco.papacoauthservice.account.application;

import com.papaco.papacoauthservice.account.application.event.AccountEventPublisher;
import com.papaco.papacoauthservice.account.domain.Account;
import com.papaco.papacoauthservice.account.domain.LoginAccount;
import com.papaco.papacoauthservice.account.domain.repository.AccountRepository;
import com.papaco.papacoauthservice.auth.oauth.OAuth2UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginAccountService implements OAuth2UserDetailsService {
    private final AccountRepository accountRepository;
    private final AccountEventPublisher accountEventPublisher;

    @Override
    public LoginAccount saveOrUpdateAccount(Map<String, Object> accountAttributes) {
        LoginAccount loginAccount = LoginAccount.of(accountAttributes);
        Optional<Account> optional = accountRepository.findByUserName(loginAccount.getUserName());

        Account account = null;
        if (optional.isPresent()) {
            account = optional.get();
            updateAccount(account, loginAccount);
            accountEventPublisher.publishUpdatedEvent(account);
        }
        if (optional.isEmpty()) {
            account = createAccount(loginAccount);
            accountRepository.save(account);
            accountEventPublisher.publishCreatedEvent(account);
        }

        return LoginAccount.of(account);
    }

    private void updateAccount(Account account, LoginAccount loginAccount) {
        account.update(loginAccount.getName(), loginAccount.getEmail());
    }

    private Account createAccount(LoginAccount loginAccount) {
        return Account.create(loginAccount.getUserName(), loginAccount.getName(), loginAccount.getEmail());
    }
}
