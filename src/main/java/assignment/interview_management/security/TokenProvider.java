package assignment.interview_management.security;

import assignment.interview_management.utils.DateTimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRED = 6 * 60 * 60;

    private static final String AUTHORITIES = "authorities";

    private static final String USERNAME = "username";


    @Value("${security.token.secret-key}")
    private String secretKey;

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).findFirst()
                .orElse(org.apache.commons.lang3.StringUtils.EMPTY);
        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES, authorities);
        claims.put(USERNAME, authentication.getName());
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.builder()
                .subject(authentication.getName())
                .claims(claims)
                .expiration(DateTimeUtils.convertToUtilDate(LocalDateTime.now().plusSeconds(TOKEN_EXPIRED)))
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            List<? extends GrantedAuthority> authorities = Optional.of(claims.get(AUTHORITIES).toString())
                    .map(SimpleGrantedAuthority::new)
                    .stream()
                    .toList();
            return new UsernamePasswordAuthenticationToken(claims.getSubject(),
                    org.apache.commons.lang3.StringUtils.EMPTY,
                    authorities);
        } catch (JwtException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
