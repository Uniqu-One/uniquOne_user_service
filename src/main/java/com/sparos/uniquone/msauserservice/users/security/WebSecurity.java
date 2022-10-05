package com.sparos.uniquone.msauserservice.users.security;

import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtAuthFilter;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtToken;
import com.sparos.uniquone.msauserservice.users.security.oauth2.CustomOauth2UserService;
import com.sparos.uniquone.msauserservice.users.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//2.6.11 대 문제가아니라 스프링 부트 버전도 안맞는듯.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService oauth2UserService;
    private final OAuth2SuccessHandler successHandler;

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().anyRequest().permitAll();

        http.oauth2Login()
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oauth2UserService);
        http.addFilterBefore(new JwtAuthFilter(jwtProvider,userRepository), UsernamePasswordAuthenticationFilter.class);
    }
}
