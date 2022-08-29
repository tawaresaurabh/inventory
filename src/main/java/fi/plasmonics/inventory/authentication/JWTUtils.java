package fi.plasmonics.inventory.authentication;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${plasmonics.backend.jwtSecret}")
    private String jwtSecret;

    @Value("${plasmonics.backend.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        InventoryUserDetails userPrincipal = (InventoryUserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        return Jwts.builder()
            //.claim("name", "Jane Doe")
            //.claim("email", "jane@example.com")
            .setSubject(userPrincipal.getUsername())
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(jwtExpirationMs, ChronoUnit.MILLIS)))
            .signWith(getSecretKey())
            .compact();

    }

    public  Jws<Claims> parseJwt(String jwtString) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(jwtString);
    }

    private Key getSecretKey(){
        return new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), SignatureAlgorithm.HS256.getJcaName());
    }


    public boolean validateJwtToken(String authToken) {
        try {
            parseJwt(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getUserName(String jwtString){
        return parseJwt(jwtString).getBody().getSubject();
    }

}
