package com.faithfulolaleru.movieRentalReactive.config;

import com.faithfulolaleru.movieRentalReactive.models.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@Slf4j
public class JwtUtils {


	@Value("${jwtSecret}")
	private String jwtSecret;

	@Value("${jwtExpirationTime}")
	private String expireTimeInMilliSec;

	public String generateToken(User user) {

		Date now = new Date();
		Map<String, Object> claim = new HashMap<>();
		claim.put("alg", "HS256");
		claim.put("typ", "JWT");
		claim.put("role", List.of(user.getRole()));

		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + Long.parseLong(expireTimeInMilliSec) * 1000))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(jwtSecret.getBytes()))   // or just regular jwtSecret
				// .setHeaderParams(claim)
				.setClaims(claim)
				.compact();
	}

	public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(jwtSecret.getBytes()))
				.parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	public Date getExpirationFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	public Boolean isTokenExpired(String token) {
		Date expirationDate = getExpirationFromToken(token);
		return expirationDate.before(new Date());
	}

	public Boolean isTokenValidated(String token) {
		return !isTokenExpired(token);
	}




	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
