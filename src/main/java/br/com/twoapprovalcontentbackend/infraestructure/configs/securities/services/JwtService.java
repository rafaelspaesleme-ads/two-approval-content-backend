package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value(value = "${spring.contexts.security.jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret));
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(this.getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();

    }

}
