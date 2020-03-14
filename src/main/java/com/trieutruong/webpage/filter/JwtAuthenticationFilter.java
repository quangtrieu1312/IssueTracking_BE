package com.trieutruong.webpage.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.service.UserService;
import com.trieutruong.webpage.util.JwtTokenProvider;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = tokenProvider.getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				String userId = tokenProvider.getUserIdFromJWT(jwt);
				User user = userService.findByUserId(userId);
				if (user != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							user, null, user.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					response.addHeader("Cache-Control", "no-cache");
					response.addHeader("Access-Control-Max-Age", "3600" );
				}
			}
		} catch (Exception ex) {
			// log.error("failed on set user authentication", ex);
		}

		filterChain.doFilter(request, response);
	}

}