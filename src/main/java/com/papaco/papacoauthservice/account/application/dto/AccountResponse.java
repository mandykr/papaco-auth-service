package com.papaco.papacoauthservice.account.application.dto;

import com.papaco.papacoauthservice.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountResponse {
    private Long id;
    private String userKey;
    private String name;
    private String email;
    private String role;

    public static AccountResponse of(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUserName(),
                account.getName(),
                account.getEmail(),
                account.getRoleKey()
        );
    }
}
