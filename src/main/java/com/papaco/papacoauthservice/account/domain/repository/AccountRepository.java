package com.papaco.papacoauthservice.account.domain.repository;

import com.papaco.papacoauthservice.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserName(String userName);
}
