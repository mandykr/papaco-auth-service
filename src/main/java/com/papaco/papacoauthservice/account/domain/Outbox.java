package com.papaco.papacoauthservice.account.domain;

import com.papaco.papacoauthservice.account.domain.event.DomainEvent;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Outbox {
    @CreatedDate
    LocalDateTime createdAt;
    boolean published;
    LocalDateTime publishedAt;

    public void publish() {
        this.published = true;
        this.publishedAt = LocalDateTime.now();
    }

    public abstract Outbox create(DomainEvent event, String payload);
}
