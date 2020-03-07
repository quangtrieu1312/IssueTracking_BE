package com.trieutruong.webpage.util;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.trieutruong.webpage.constant.JwtConstants;
import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.exception.BadInputException;

import io.jsonwebtoken.*;

@Component
public class JwtTokenProvider {
	private final String JWT_SECRET = JwtConstants.SECRET_KEY;

	private final long JWT_EXPIRATION = 604800000L;

	public String generateToken(User user) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder().setSubject(user.getUserId()).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	public String getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String authToken) throws BadInputException {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
//        	throw new BadInputException("Invalid JWT token");
//            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
//        	throw new BadInputException("Expired JWT token");
//            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
//        	throw new BadInputException("Unsupported JWT token");
//            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
//        	throw new BadInputException("JWT claims string is empty");
//            log.error("JWT claims string is empty.");
        }
        return false;
    }
	
	public String getJwtFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie :cookies)
			if (cookie.getName().equals("AUTH_JWT"))
				return cookie.getValue();
		return null;
	}
}