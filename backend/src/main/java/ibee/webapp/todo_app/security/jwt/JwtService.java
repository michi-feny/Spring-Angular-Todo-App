package ibee.webapp.todo_app.security.jwt;


import ibee.webapp.todo_app.security.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Component
public class JwtService {
    @Value("${jwt.secret.key}")
    private String SECRET;
    @Value("${jwt.expiration.time}")
    private Long EXPIRATION_TIME;
    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXPIRATION_TIME;

    public String generateAccessToken(AuthenticatedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("name", userDetails.getName());
        claims.put("roles", userDetails.getRoleStrings().toString());
        claims.put("tokenType", "access");
        return createToken(claims, userDetails.getUsername(), EXPIRATION_TIME);
    }

    public String generateRefreshToken(AuthenticatedUser userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");
        claims.put("id", userDetails.getId());

        return createToken(claims, userDetails.getUsername(), REFRESH_EXPIRATION_TIME);
    }


    private String createToken(Map<String, Object> claims, String email, Long expirationMs) {
        Date expiredAt = new Date(System.currentTimeMillis() + expirationMs);
        try {
            return Jwts.builder()
                    .claims(claims)
                    .claim(Claims.SUBJECT, email)
                    .claim(Claims.ISSUED_AT, new Date(System.currentTimeMillis()))
                    .claim(Claims.EXPIRATION, expiredAt)
                    .signWith(getSignKey())
                    .header()
                    .add("type", "JWT")
                    .and()
                    .compact();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            throw new IllegalStateException(
                    "Error while creating the token: Invalid claims or builder config.",
                    ex
            );
        }
    }

    public Boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token);

        return "refresh".equals(claims.get("tokenType"));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserId(String token) {
        Long userId = extractClaim(token, claims -> claims.get("id", Long.class));

        if (userId == null) {
            throw new JwtException("The required 'id' Claim is not im token or is null.");
        }

        return userId;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

    private Claims extractAllClaims(String token) {
        SecretKey secretKey = getSignKey();
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        } catch (RuntimeException e) {
            throw new RuntimeException("Critical Config error: JWT secret key is invalid or wrong.", e);
        }
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}