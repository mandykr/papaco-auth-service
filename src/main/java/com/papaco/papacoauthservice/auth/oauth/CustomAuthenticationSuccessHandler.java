package com.papaco.papacoauthservice.auth.oauth;

import com.papaco.papacoauthservice.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = tokenProvider.createToken(authentication);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(tokenProvider.AUTHORIZATION_HEADER, "Bearer " + jwt);
    }
}
