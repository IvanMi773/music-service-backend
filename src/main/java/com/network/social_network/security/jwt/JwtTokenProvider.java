package com.network.social_network.security.jwt;

import com.network.social_network.exception.CustomException;
import com.network.social_network.security.OUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //Todo: create secure key. Maybe move it from program
    private String key = "jwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettokenjwtsecrettoken";
    private long validityInMilliseconds = 3600000;

    @Autowired
    private OUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String generateToken(String username, String role) {
        Date validity = new Date(new Date().getTime() + validityInMilliseconds);

        String token = Jwts
                .builder()
                .setSubject(username)
                .claim("authorities", role)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return token;
    }

    public Authentication getAuthentication(String token) {
        var userDetails = userDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername (String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken (String token) {
        try {
            //Todo: when exception is throwing, send it to client. Now it outputs in console
            Jws<Claims> claimsJws = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
