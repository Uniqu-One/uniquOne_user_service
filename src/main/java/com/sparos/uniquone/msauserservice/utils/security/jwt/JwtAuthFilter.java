package com.sparos.uniquone.msauserservice.utils.security.jwt;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
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

//@RequiredArgsConstructor

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider;

    private UserRepository userRepository;

    public JwtAuthFilter(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    public JwtAuthFilter(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = ((HttpServletRequest)request).getHeader("token");

        if(token != null && jwtProvider.verifyToken(token)){
            String email = jwtProvider.getEmailId(token);

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
