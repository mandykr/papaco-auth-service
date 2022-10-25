package com.papaco.papacoauthservice.account.domain;

import com.papaco.papacoauthservice.auth.oauth.OAuth2UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class LoginAccount implements OAuth2UserDetails {
    private Long id;
    private String userName;
    private String name;
    private String email;
    private AccountRole role;

    public LoginAccount(String userName, String name, String email, AccountRole role) {
        this(null, userName, name, email, role);
    }

    @Override
    public String getRoleKey() {
        return this.role.getKey();
    }

    public static LoginAccount of(Account account) {
        return new LoginAccount(account.getId(), account.getUserName(), account.getName(), account.getEmail(), account.getRole());
    }

    public static LoginAccount of(Map<String, Object> accountAttributes) {
        return new LoginAccount(
                (String) accountAttributes.get("userName"),
                (String) accountAttributes.get("name"),
                (String) accountAttributes.get("email"),
                AccountRole.USER
        );
    }
}
