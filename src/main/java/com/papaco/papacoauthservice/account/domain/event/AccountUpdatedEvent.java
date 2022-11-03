package com.papaco.papacoauthservice.account.domain.event;

import com.papaco.papacoauthservice.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class AccountUpdatedEvent implements AccountEvent {
    private EventType eventType;
    private Long aggregateId;
    private String name;
    private String email;

    public static AccountUpdatedEvent of(Account account) {
        return new AccountUpdatedEvent(
                EventType.UPDATED,
                account.getId(),
                account.getName(),
                account.getEmail()
        );
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public Long getAggregateId() {
        return aggregateId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
