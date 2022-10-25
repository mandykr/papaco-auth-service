package com.papaco.papacoauthservice.auth;

import com.papaco.papacoauthservice.auth.jwt.JwtAccessDeniedHandler;
import com.papaco.papacoauthservice.auth.jwt.JwtAuthenticationEntryPoint;
import com.papaco.papacoauthservice.auth.jwt.JwtSecurityConfig;
import com.papaco.papacoauthservice.auth.jwt.TokenProvider;
import com.papaco.papacoauthservice.auth.oauth.CustomAuthenticationSuccessHandler;
import com.papaco.papacoauthservice.auth.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
//    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()

                // enable h2-console
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/", "/login/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)

                .and()
                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
                .permitAll()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));


        http.logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

        return http.build();
    }
}
