package com.trieutruong.webpage.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.request.ActivateRequest;
import com.trieutruong.webpage.request.LoginRequest;
import com.trieutruong.webpage.request.SignUpRequest;

public interface UserService extends UserDetailsService {
	void login(LoginRequest req, HttpServletRequest request, HttpServletResponse response);

	User findByUserId(String userId);

	void signUp(SignUpRequest req) throws IOException;

	void activateByToken(String activateToken) throws BadInputException;

	void sendActivateToken(String username, String email) throws IOException;
	
	User findByJWT(String jwt) throws BadInputException;
	
	User findByHttpRequest(HttpServletRequest httpRequest) throws BadInputException;
}
