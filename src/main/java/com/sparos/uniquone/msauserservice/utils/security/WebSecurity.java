package com.sparos.uniquone.msauserservice.utils.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import com.sparos.uniquone.msauserservice.utils.security.exception.CustomAuthenticationEntryPoint;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtAuthFilter;
import com.sparos.uniquone.msauserservice.utils.security.oauth2.CustomOauth2UserService;
import com.sparos.uniquone.msauserservice.utils.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;

    @Value("${token.secret}")
    private String key;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().anyRequest().permitAll()
                .and()
                .addFilter(getAuthenticationFilter());
//        http.authorizeRequests()
//                .antMatchers("/**")
//                .access("hasIpAddress('" + "172.18.0.8" + "')")
//                .and()
//                .addFilter(getAuthenticationFilter());

        http.logout().logoutUrl("/logout");

//        http.oauth2Login().loginPage("/token/expired")
        http.oauth2Login()
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oauth2UserService);


        http.addFilterBefore(new JwtAuthFilter(key, userRepository), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, objectMapper);

        authenticationFilter.setFilterProcessesUrl("/login/oauth");

        return authenticationFilter;
    }


}
