package com.papaco.papacoauthservice.account.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String key;
}
