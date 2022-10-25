package com.papaco.papacoauthservice.account.domain;

import com.papaco.papacoauthservice.account.domain.event.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Outbox {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long aggregateId;

    @Column(nullable = false)
    private String aggregateType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Lob
    @Column(nullable = false)
    private String payload;

    public Outbox(Long aggregateId, String aggregateType, EventType eventType, String payload) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.payload = payload;
    }
}
