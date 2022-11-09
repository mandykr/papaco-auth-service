package com.papaco.papacoauthservice.unit.account.application;

import com.papaco.papacoauthservice.account.domain.Outbox;
import com.papaco.papacoauthservice.account.domain.event.DomainEvent;
import com.papaco.papacoauthservice.account.domain.event.EventType;

public class FakeOutbox extends Outbox {
    private Long id;
    private Long aggregateId;
    private String aggregateType;
    private EventType eventType;
    private String payload;

    public FakeOutbox(Long aggregateId, String aggregateType, EventType eventType, String payload) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.payload = payload;
    }

    @Override
    public Outbox create(DomainEvent event, String payload) {
        return new FakeOutbox(event.getAggregateId(), event.getClass().getSimpleName(), event.getEventType(), payload);
    }
}
