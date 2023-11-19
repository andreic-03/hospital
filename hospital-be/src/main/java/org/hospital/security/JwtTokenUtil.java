package org.hospital.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hospital.security.model.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtil {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2550185165626007488L;

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hours

	private final Log logger = LogFactory.getLog(getClass());

    private final String secret;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
		this.secret = secret;
	}

	//retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the token is expired without throwing an exception if so.
     * 
     * @param token the token to check
     * @return true if still valid, false otherwise
     */
    public Boolean isTokenExpired(String token) {
    	try {
    		final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
    	} catch (ExpiredJwtException e) {
    		logger.info("ExpiredJwtException suppressed.");
    		logger.debug(e.getMessage(), e);
    		return true;
    	}
    }

    //generate token for user
    public String generateToken(AppUserPrincipal user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", authoritiesToString(user.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private String authoritiesToString(Collection<? extends GrantedAuthority> authorities) {
        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }


    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired", ex);
        } catch (IllegalArgumentException ex) {
            log.error("Token is null, empty or only whitespace", ex);
        } catch (MalformedJwtException ex) {
            log.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            log.error("Signature validation failed");
        }

        return false;
    }
}
