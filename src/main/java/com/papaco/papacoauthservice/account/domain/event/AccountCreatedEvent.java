package com.papaco.papacoauthservice.account.domain.event;

import com.papaco.papacoauthservice.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class AccountCreatedEvent implements AccountEvent {
    private EventType eventType;
    private Long aggregateId;
    private String userName;
    private String name;
    private String email;
    private String roleKey;

    public static AccountCreatedEvent of(Account account) {
        return new AccountCreatedEvent(
                EventType.CREATED,
                account.getId(),
                account.getUserName(),
                account.getName(),
                account.getEmail(),
                account.getRoleKey()
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

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleKey() {
        return roleKey;
    }
}
