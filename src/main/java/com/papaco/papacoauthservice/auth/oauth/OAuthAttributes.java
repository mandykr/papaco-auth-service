package com.papaco.papacoauthservice.auth.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String registrationId;
    private String userName;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(String registrationId,
                           String userName,
                           String name,
                           String email) {
        this.registrationId = registrationId;
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .registrationId(registrationId)
                .userName(String.valueOf(attributes.get(userNameAttributeName)))
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .build();
    }
}
