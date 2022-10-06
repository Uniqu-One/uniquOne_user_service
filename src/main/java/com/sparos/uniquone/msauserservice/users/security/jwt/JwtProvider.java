package com.sparos.uniquone.msauserservice.users.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//@Component
@Slf4j
@RequiredArgsConstructor
@Service
public class JwtProvider {
    private final Environment env;


    public JwtToken generateToken(String email,String role){
        //닉네임을 여기서 꺼내쓸일이 있을까.
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", email);
        claims.put("Role", role);

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
//    public JwtToken generateToken(String email,String role){
//        //닉네임을 여기서 꺼내쓸일이 있을까.
//        Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
//        claims.put("id", email);
//        claims.put("Role", authentication.getAuthorities());
//
//        return new JwtToken(
//                Jwts.builder()
//                        .setClaims(claims)
//                        .setIssuedAt(new Date())
//                        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
//                        .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
//                        .compact(),
//                Jwts.builder()
//                        .setClaims(claims)
//                        .setIssuedAt(new Date())
//                        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("refreshToken.expiration_time"))))
//                        .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
//                        .compact()
//
//        );
//    }

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

    public String getEmailId(String token){
        return Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token).getBody().getSubject();
    }
    public String getEmailId(HttpServletRequest request){

        String token = request.getHeader(env.getProperty("token.name"));

        return Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token).getBody().getSubject();
    }

}