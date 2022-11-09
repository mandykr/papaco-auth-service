package com.papaco.papacoauthservice.account.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    private Account(String userName, String name, String email, AccountRole role) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static Account create(String userName, String name, String email) {
        return new Account(userName, name, email, AccountRole.USER);
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
