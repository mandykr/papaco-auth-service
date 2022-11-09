package com.papaco.papacoauthservice.account.domain.repository;

import com.papaco.papacoauthservice.account.domain.AccountOutbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface AccountOutboxRepository extends JpaRepository<AccountOutbox, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Page<AccountOutbox> findAllByPublishedIsFalseOrderByIdAsc(Pageable pageable);
}
