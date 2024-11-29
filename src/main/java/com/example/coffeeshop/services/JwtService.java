package com.example.coffeeshop.services;

import com.example.coffeeshop.dto.auth.JwtTokenDTO;
import com.example.coffeeshop.mappers.JwtTokenMapper;
import com.example.coffeeshop.models.JwtToken;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.token.secret}")
    private String jwtSecretKey;

    @Value("${jwt.token.expiration}")
    private Long jwtExpiration;

    private final JwtRepository jwtRepository;
    private final JwtTokenMapper jwtTokenMapper;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public JwtTokenDTO generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities());
        return generateToken(claims, user);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenRevoked(token));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private JwtTokenDTO generateToken(Map<String, Object> extraClaims, User user) {
        String jwtToken = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        invalidateOldTokens(user.getId());
        JwtToken newJwtToken = jwtRepository.save(new JwtToken(jwtToken, false, user));
        return jwtTokenMapper.toJwtTokenDTO(newJwtToken);
    }

    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException{
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void invalidateToken(String token) {
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        claims.setExpiration(new Date());
        JwtToken jwtToken = jwtRepository.findByToken(token.replace("Bearer ", ""));
        jwtToken.setRevoked(true);
        jwtRepository.save(jwtToken);
    }

    public boolean isTokenRevoked(String token) {
        JwtToken jwtToken = jwtRepository.findByToken(token);
        return jwtToken != null && jwtToken.isRevoked();
    }

    private void invalidateOldTokens(Long userId) {
        List<JwtToken> validOldTokens = jwtRepository.findValidTokensByUserId(userId);
        validOldTokens.forEach(token -> token.setRevoked(true));
        jwtRepository.saveAll(validOldTokens);
    }

}
