package com.sparos.uniquone.msauserservice.users.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

//@Component
@Slf4j
@RequiredArgsConstructor
@Service
public class JwtProvider {
    private final Environment env;


    public JwtToken generateToken(Authentication authentication,String email){
        //닉네임을 여기서 꺼내쓸일이 있을까.
        Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
        claims.put("id", email);
        claims.put("Role", authentication.getAuthorities());

        return new JwtToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                        .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("refreshToken.expiration_time"))))
                        .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                        .compact()

        );
    }

    //토큰 검증 ?
    public boolean verifyToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public String getPkId(String token){
        return Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token).getBody().getSubject();
    }

}