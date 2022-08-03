package com.senla.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenSupplier {

    private final UserDetailsService userDetailsService;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${expirationTimeInSeconds}")
    private Long expirationTimeInSeconds;
    @Value("${authorization.header}")
    private String authorizationHeader;

    @PostConstruct
    protected void init() {
        secretKey = Base64.toBase64String(secretKey.getBytes());
    }

    public String generateToken(String login, String role) {
        Date now = new Date();
        Date valid = new Date(now.getTime() + expirationTimeInSeconds * 1000);

        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("JWT token validation failed", e);
            throw new IllegalArgumentException("JWT token validation failed");
        }
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getLoginFromToken(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
