package com.sparos.uniquone.msauserservice.utils.security.jwt;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;



@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final String key;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = ((HttpServletRequest)request).getHeader("token");

        if(token != null && JwtProvider.verifyToken(token)){
            String email = JwtProvider.getUserNickName(token);

            Optional<Users> users = userRepository.findByEmail(email);

            Authentication auth = getAuthentication(users.get());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(Users users) {
        return new UsernamePasswordAuthenticationToken(users, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
